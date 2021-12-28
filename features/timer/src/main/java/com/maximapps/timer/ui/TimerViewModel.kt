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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximapps.core.ui.StateOwner
import com.maximapps.timer.domain.interactors.TimerInteractor
import com.maximapps.timer.domain.models.ExecutionMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val interactor: TimerInteractor
) : ViewModel(), StateOwner<ExecutionMode> {
    override val state = interactor.readExecutionMode()
        .stateIn(viewModelScope, SharingStarted.Lazily, ExecutionMode.Manual)

    fun collectTimerState(action: (Long) -> Unit) {
        viewModelScope.launch { interactor.collectTimerState(action) }
    }

    fun startShortBreak(breakDuration: Long) {
        interactor.startShortBreak(breakDuration)
    }

    fun stopShortBreak() {
        interactor.stopShortBreak()
    }
}
