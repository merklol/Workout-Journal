/*
 * Copyright (c) 2021 Maxim Smolyakov
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.maximapps.workoutNote.ui.note

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.maximapps.core.ext.capitalized
import com.maximapps.core.ext.launchAfterStarted
import com.maximapps.core.ext.onStates
import com.maximapps.core.ext.setActionBarTitle
import com.maximapps.core.ui.recyclerview.ITEM_IS_AT_BOTTOM
import com.maximapps.core.ui.recyclerview.ITEM_IS_ON_TOP
import com.maximapps.core.ui.recyclerview.decorations.SpacingItemDecoration
import com.maximapps.core.ui.recyclerview.decorations.defaultDividerItemDecoration
import com.maximapps.core.ui.recyclerview.findItemPosition
import com.maximapps.core.ui.recyclerview.listAdapterOf
import com.maximapps.core.ui.recyclerview.setOnSwipedListener
import com.maximapps.core.utils.navigation.navigate
import com.maximapps.core.utils.showUndoMessage
import com.maximapps.workoutNote.R
import com.maximapps.workoutNote.databinding.FragmentNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment @Inject constructor() : Fragment(R.layout.fragment_note) {
    private val args: NoteFragmentArgs by navArgs()
    private val binding: FragmentNoteBinding by viewBinding()
    private val viewModel: NoteViewModel by viewModels()

    private val adapter = listAdapterOf { ListViewHolder(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle(args.properties.title)
        setupSessionList(requireContext())
        renderSessionListState()
        binding.newWorkoutNote.setOnClickListener(::onClick)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.actionDetails -> {
            adapter.unsubscribeFromDataChanges()
            navigate(NoteFragmentDirections.actionNoteFragmentToDetailsFragment(args.properties))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun setupSessionList(context: Context) {
        binding.sessionList.addItemDecoration(defaultDividerItemDecoration(context))
        binding.sessionList.addItemDecoration(SpacingItemDecoration(context, space = 16))
        adapter.subscribeToDataChanges(::onDataChanged)
        binding.sessionList.adapter = adapter
        onStates(viewModel, adapter::setState)
        binding.sessionList.setOnSwipedListener(context, ::onSwiped)
    }

    private fun renderSessionListState() = launchAfterStarted {
        adapter.state.collectLatest {
            binding.progressIndicator.isVisible = it.isLoading
            binding.sessionListHint.isVisible = it.isEmpty
            binding.sessionList.isInvisible = it.isLoading || it.isEmpty
            binding.newWorkoutNote.isVisible = it.isEmpty || !it.isLoading
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClick(view: View) {
        adapter.unsubscribeFromDataChanges()
        navigate(NoteFragmentDirections.actionNoteFragmentToSessionFragment(args.properties))
    }

    private fun onDataChanged(itemPosition: Int) = with(binding.sessionList) {
        when (findItemPosition(itemPosition)) {
            ITEM_IS_ON_TOP, ITEM_IS_AT_BOTTOM -> scrollToPosition(itemPosition)
        }
    }

    private fun onSwiped(position: Int) = with(adapter.getItemAt(position)) {
        viewModel.deleteSession(args.properties.id, this)
        showUndoMessage(requireView(), getString(R.string.undo_message, name.capitalized)) {
            viewModel.restoreSession(args.properties.id, this)
        }
    }
}
