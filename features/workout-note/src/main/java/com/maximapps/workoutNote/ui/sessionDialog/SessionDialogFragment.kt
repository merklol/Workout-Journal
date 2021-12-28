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

package com.maximapps.workoutNote.ui.sessionDialog

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.maximapps.core.ext.hideKeyboard
import com.maximapps.core.ext.onStates
import com.maximapps.core.ext.toMilliseconds
import com.maximapps.core.utils.navigation.GlobalDirections
import com.maximapps.core.utils.navigation.GlobalNavHost
import com.maximapps.core.utils.navigation.navigate
import com.maximapps.workoutNote.R
import com.maximapps.workoutNote.databinding.FragmentSessionDialogBinding
import com.maximapps.workoutNote.domain.storage.models.UserInput
import com.maximapps.workoutNote.domain.validators.models.ValidationResult
import com.maximapps.workoutNote.ext.doOnTextChanged
import com.maximapps.workoutNote.ext.text
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SessionDialogFragment @Inject constructor(
    private val directions: GlobalDirections,
    private val host: GlobalNavHost
) : Fragment(R.layout.fragment_session_dialog) {
    private val args: SessionDialogFragmentArgs by navArgs()
    private val viewModel: SessionDialogViewModel by viewModels()
    private val binding: FragmentSessionDialogBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSessionFormValues()
        onSessionStateChanged()
        setTextChangedListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_session, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.actionDone -> with(binding) {
            viewModel.saveSession(
                args.properties.id,
                UserInput(nameTextField.text, repsTextField.text, breakDurationField.text)
            )
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        view?.hideKeyboard()
    }

    private fun setTextChangedListeners() = with(binding) {
        nameTextField.doOnTextChanged { nameTextField.error = null }
        repsTextField.doOnTextChanged { repsTextField.error = null }
        breakDurationField.doOnTextChanged { breakDurationField.error = null }
    }

    private fun setSessionFormValues() = with(viewModel.getUserInput()) {
        binding.nameTextField.text = name
        binding.repsTextField.text = repetitions
        binding.breakDurationField.text = breakDuration
    }

    private fun onSessionStateChanged() = onStates(viewModel) {
        when (it) {
            is SessionDialogFormStates.ValidUserInput ->
                with(binding.breakDurationField.text.toLong().toMilliseconds()) {
                    val props = args.properties.copy(breakDuration = this)
                    navigate(directions.fromSessionDialogToTimer(props), host)
                }
            is SessionDialogFormStates.InvalidInput -> renderErrorMessages(it.errors)
        }
    }

    private fun renderErrorMessages(errors: List<ValidationResult>) = errors.forEach {
        when (it) {
            is ValidationResult.InvalidName ->
                binding.nameTextField.error = getString(it.error)
            is ValidationResult.InvalidRepetitions ->
                binding.repsTextField.error = getString(it.error)
            is ValidationResult.InvalidBreakDuration ->
                binding.breakDurationField.error = getString(it.error)
        }
    }
}
