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

package com.maximapps.workoutNote.data

import android.content.SharedPreferences
import com.maximapps.core.di.UserInputSharedPreferences
import com.maximapps.workoutNote.domain.storage.UserInputStorage
import com.maximapps.workoutNote.domain.storage.models.UserInput
import javax.inject.Inject

private const val NAME = "name"
private const val NAME_DEF_VALUE = ""
private const val REPETITIONS = "repetitions"
private const val REPETITIONS_DEF_VALUE = ""
private const val BREAK_DURATION = "break-duration"
private const val BREAK_DURATION_DEF_VALUE = "15"

/**
 * Shared Preferences implementation of [UserInputStorage] interface using [SharedPreferences].
 */
class SharedPrefUserInputStorage @Inject constructor(
    @UserInputSharedPreferences private val sharedPreferences: SharedPreferences
) : UserInputStorage {

    override fun saveUserInput(userInput: UserInput) {
        sharedPreferences.edit()
            .putString(NAME, userInput.name)
            .putString(REPETITIONS, userInput.repetitions)
            .putString(BREAK_DURATION, userInput.breakDuration)
            .apply()
    }

    override fun getUserInput() = UserInput(
        name = sharedPreferences.getString(NAME, NAME_DEF_VALUE) ?: "",
        repetitions = sharedPreferences.getString(REPETITIONS, REPETITIONS_DEF_VALUE) ?: "",
        breakDuration = sharedPreferences.getString(BREAK_DURATION, BREAK_DURATION_DEF_VALUE) ?: ""
    )
}
