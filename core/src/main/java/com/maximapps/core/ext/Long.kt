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

package com.maximapps.core.ext

import java.util.concurrent.TimeUnit

/**
 * Converts a Long to a formatted string representation.
 */
fun Long.toFormattedTime() =
    String.format("%02d:%02d", this.toMinutes(), this.toSeconds())

/**
 * Converts a Long to milliseconds.
 */
fun Long.toMilliseconds() = TimeUnit.SECONDS.toMillis(this)

/**
 * Converts a Long to minutes.
 */
private fun Long.toMinutes() = TimeUnit.MILLISECONDS.toMinutes(this) -
        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this))

/**
 * Converts a Long to seconds.
 */
private fun Long.toSeconds() = TimeUnit.MILLISECONDS.toSeconds(this) -
        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))
