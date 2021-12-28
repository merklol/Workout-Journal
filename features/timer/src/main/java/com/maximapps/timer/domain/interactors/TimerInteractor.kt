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

package com.maximapps.timer.domain.interactors

import android.content.SharedPreferences
import com.maximapps.core.di.DefaultSharedPreferences
import com.maximapps.timer.domain.models.ExecutionMode
import com.maximapps.timer.domain.repositories.TimerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TimerInteractor @Inject constructor(
    @DefaultSharedPreferences private val sharedPreferences: SharedPreferences,
    private val repository: TimerRepository
) {

    companion object {
        /**
         * Determines whether a countdown timer should start automatically.
         */
        private const val START_BREAKS_AUTOMATICALLY = "start_breaks_automatically"
    }

    /**
     * Collects the latest countdown timer state.
     */
    suspend fun collectTimerState(action: (Long) -> Unit) {
        repository.subscribe(action)
    }

    /**
     * Starts a countdown timer for a short break.
     */
    fun startShortBreak(breakDuration: Long) {
        repository.startService(breakDuration)
    }

    /**
     * Stops a countdown timer for a short break.
     */
    fun stopShortBreak() {
        repository.stopService()
    }

    /**
     * Reads countdown timer execution mode from the storage.
     */
    fun readExecutionMode() = flowOf(
        when (sharedPreferences.getBoolean(START_BREAKS_AUTOMATICALLY, false)) {
            true -> ExecutionMode.SelfExecuting
            else -> ExecutionMode.Manual
        }
    ).flowOn(Dispatchers.IO)
}
