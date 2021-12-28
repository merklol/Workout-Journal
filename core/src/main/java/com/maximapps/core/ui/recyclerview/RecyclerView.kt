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

package com.maximapps.core.ui.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Fully visible Recyclerview item is at 0 position.
 */
const val ITEM_IS_ON_TOP = 0

/**
 * Fully visible Recyclerview item is at the last position.
 */
const val ITEM_IS_AT_BOTTOM = 1

/**
 * Fully visible Recyclerview item is neither at zero position nor last position.
 */
const val OTHER_POSITION = -1

/**
 * Returns the adapter position of the fully visible view.
 */
fun RecyclerView.findItemPosition(position: Int) = with(layoutManager as LinearLayoutManager) {
    when {
        position == 0 && position == findFirstCompletelyVisibleItemPosition() ->
            ITEM_IS_ON_TOP
        position == itemCount - 1 && position - 1 == findLastCompletelyVisibleItemPosition() ->
            ITEM_IS_AT_BOTTOM
        else ->
            OTHER_POSITION
    }
}
