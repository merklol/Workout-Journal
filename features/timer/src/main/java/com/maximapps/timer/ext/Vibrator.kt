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

package com.maximapps.timer.ext

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.VibrationEffect.createWaveform
import android.os.Vibrator
import com.maximapps.timer.data.models.VibrationPattern

/**
 * Starts vibration.
 */
@Suppress("DEPRECATION")
fun Vibrator.vibrate(pattern: VibrationPattern) = with(pattern) {
    when {
        SDK_INT >= Build.VERSION_CODES.O -> vibrate(
            createWaveform(longArrayOf(delay, vibrate, sleep), 0)
        )
        else -> vibrate(longArrayOf(delay, vibrate, sleep), 0)
    }
}
