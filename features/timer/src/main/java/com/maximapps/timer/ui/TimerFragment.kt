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

package com.maximapps.timer.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.button.MaterialButton
import com.maximapps.core.ext.onStates
import com.maximapps.core.ext.setActionBarTitle
import com.maximapps.core.ext.toFormattedTime
import com.maximapps.core.utils.navigation.GlobalDirections
import com.maximapps.core.utils.navigation.GlobalNavHost
import com.maximapps.core.utils.navigation.navigate
import com.maximapps.timer.R
import com.maximapps.timer.data.services.TimerService.Companion.TIME_IS_UP
import com.maximapps.timer.databinding.FragmentTimerBinding
import com.maximapps.timer.domain.models.ExecutionMode
import com.maximapps.timer.ext.setOnBackPressedCallback
import com.maximapps.timer.ext.timerDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TimerFragment @Inject constructor(
    private val directions: GlobalDirections, private val host: GlobalNavHost
) : Fragment(R.layout.fragment_timer) {
    private val args: TimerFragmentArgs by navArgs()
    private val viewModel: TimerViewModel by viewModels()
    private val binding: FragmentTimerBinding by viewBinding()

    private val dialog = timerDialog(onClick = {
        viewModel.stopShortBreak()
        navigate(directions.fromTimerToWorkoutNote(args.properties.copy()), host)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBarTitle(args.properties.title)
        setOnBackPressedCallback(::onBackPressed)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onReadTimerExecutionMode()
        binding.timerButton.addOnCheckedChangeListener(::onChecked)
        binding.timerView.text = args.properties.breakDuration.toFormattedTime()
        viewModel.collectTimerState(::renderTimerState)
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog.value.dismiss()
    }

    private fun onReadTimerExecutionMode() = onStates(viewModel) {
        if (it is ExecutionMode.SelfExecuting) {
            binding.timerButton.isChecked = true
        }
    }

    private fun onBackPressed() {
        if (binding.timerButton.isChecked) {
            viewModel.stopShortBreak()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onChecked(button: MaterialButton, isChecked: Boolean) = when {
        isChecked -> {
            viewModel.startShortBreak(args.properties.breakDuration)
            binding.timerButton.text = getString(R.string.stop_button_text)
        }
        else -> {
            viewModel.stopShortBreak()
            navigate(directions.fromTimerToWorkoutNote(args.properties.copy()), host)
        }
    }

    private fun renderTimerState(state: Long) {
        when (state) {
            TIME_IS_UP -> {
                dialog.value.show()
                binding.timerView.text = getString(R.string.timer_is_up)
            }
            else -> binding.timerView.text = state.toFormattedTime()
        }
    }
}
