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

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.maximapps.timer.R

/**
 * Set a OnBackPressedCallback.
 */
fun Fragment.setOnBackPressedCallback(block: () -> Unit) {
    val activity = requireActivity()
    activity.onBackPressedDispatcher
        .addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                remove()
                block()
                activity.onBackPressed()
            }
        })
}

/**
 * Creates a TimerDialog with the onClick callback supplied to this function.
 */
fun Fragment.timerDialog(onClick: () -> Unit) = lazy {
    with(requireContext()) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.timer_dialog_title))
            .setMessage(getString(R.string.timer_dialog_message))
            .setPositiveButton(getString(R.string.timer_dialog_button_text)) { _, _ -> onClick() }
            .create()
    }
}
