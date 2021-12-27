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

package com.maximapps.workoutjournal.navigation

import com.maximapps.core.utils.navigation.GlobalDirections
import com.maximapps.core.utils.navigation.models.Properties
import com.maximapps.main.ui.MainFragmentDirections
import com.maximapps.timer.ui.TimerFragmentDirections
import com.maximapps.workoutNote.ui.sessionDialog.SessionDialogFragmentDirections
import javax.inject.Inject

/**
 * Default implementation of [GlobalDirections] interface.
 */
class AppDirections @Inject constructor() : GlobalDirections {
    override fun fromSessionDialogToTimer(properties: Properties) =
        SessionDialogFragmentDirections.actionSessionFragmentToTimerFragment(properties)

    override fun fromMainToWorkoutNote(properties: Properties) =
        MainFragmentDirections.actionMainFragmentToWorkoutNoteGraph(properties)

    override fun fromTimerToWorkoutNote(properties: Properties) =
        TimerFragmentDirections.actionTimerFragmentToNoteFragment(properties)
}
