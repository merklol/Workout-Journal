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

package com.maximapps.workoutNote.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import com.maximapps.core.domain.models.Session
import com.maximapps.core.ext.toFormattedTime
import com.maximapps.core.ui.recyclerview.ListAdapter
import com.maximapps.core.ui.recyclerview.getString
import com.maximapps.workoutNote.R
import com.maximapps.workoutNote.databinding.SessionListItemBinding

/**
 * ViewHolder for the sessions dynamic list.
 */
class ListViewHolder(private val binding: SessionListItemBinding) :
    ListAdapter.ViewHolder<Session>(binding) {

    companion object {
        operator fun invoke(parent: ViewGroup): ListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = SessionListItemBinding.inflate(inflater, parent, false)
            return ListViewHolder(binding)
        }
    }

    override fun bind(item: Session) {
        binding.sessionName.text = item.name
        binding.repetitions.text = getString(R.string.session_list_item_text_1, item.repetitions)
        binding.breakDuration.text = getString(
            R.string.session_list_item_text_2,
            item.breakDuration.toFormattedTime()
        )
    }
}
