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

package com.maximapps.main.ui.history

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.maximapps.core.domain.models.WorkoutNote
import com.maximapps.core.ext.capitalized
import com.maximapps.core.ext.launchAfterStarted
import com.maximapps.core.ext.onStates
import com.maximapps.core.ui.recyclerview.ITEM_IS_AT_BOTTOM
import com.maximapps.core.ui.recyclerview.ITEM_IS_ON_TOP
import com.maximapps.core.utils.navigation.GlobalDirections
import com.maximapps.core.utils.navigation.GlobalNavHost
import com.maximapps.core.utils.navigation.models.toProperties
import com.maximapps.core.utils.navigation.navigate
import com.maximapps.core.ui.recyclerview.decorations.SpacingItemDecoration
import com.maximapps.core.ui.recyclerview.findItemPosition
import com.maximapps.core.ui.recyclerview.listAdapterOf
import com.maximapps.core.ui.recyclerview.setOnSwipedListener
import com.maximapps.core.utils.showUndoMessage
import com.maximapps.main.R
import com.maximapps.main.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment(
    private val directions: GlobalDirections,
    private val host: GlobalNavHost
) : Fragment(R.layout.fragment_history) {
    private val viewModel: HistoryViewModel by viewModels()
    private val binding: FragmentHistoryBinding by viewBinding()

    private val adapter = listAdapterOf { ListViewHolder(it, ::onListItemClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHistoryList(requireContext())
        renderHistoryListStates()
        binding.newWorkoutNote.setOnClickListener(::onClick)
    }

    private fun setupHistoryList(context: Context) {
        binding.historyList.addItemDecoration(SpacingItemDecoration(context))
        adapter.subscribeToDataChanges(::onDataChanged)
        binding.historyList.adapter = adapter
        onStates(viewModel, adapter::setState)
        binding.historyList.setOnSwipedListener(context, ::onSwiped)
    }

    private fun renderHistoryListStates() = launchAfterStarted {
        adapter.state.collectLatest {
            binding.progressIndicator.isVisible = it.isLoading
            binding.historyListHint.isVisible = it.isEmpty
            binding.historyList.isInvisible = it.isLoading || it.isEmpty
            binding.newWorkoutNote.isVisible = it.isEmpty || !it.isLoading
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClick(view: View) = lifecycleScope.launch {
        adapter.unsubscribeFromDataChanges()
        val res = viewModel.addNewWorkoutNote()
        navigate(directions.fromMainToWorkoutNote(res.toProperties()), host)
    }

    private fun onSwiped(position: Int) = with(adapter.getItemAt(position)) {
        viewModel.deleteWorkoutNote(this)
        showUndoMessage(requireView(), getString(R.string.undo_message, title.capitalized)) {
            viewModel.restoreWorkoutNote(this)
        }
    }

    private fun onListItemClick(workoutNote: WorkoutNote) {
        adapter.unsubscribeFromDataChanges()
        navigate(directions.fromMainToWorkoutNote(workoutNote.toProperties()), host)
    }

    private fun onDataChanged(itemPosition: Int) = with(binding.historyList) {
        when (findItemPosition(itemPosition)) {
            ITEM_IS_ON_TOP, ITEM_IS_AT_BOTTOM -> scrollToPosition(itemPosition)
        }
    }
}
