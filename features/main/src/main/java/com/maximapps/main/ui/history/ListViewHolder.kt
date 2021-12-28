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

package com.maximapps.main.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import com.maximapps.core.domain.models.WorkoutNote
import com.maximapps.core.ui.recyclerview.ListAdapter
import com.maximapps.main.databinding.HistoryListItemBinding

/**
 * ViewHolder for the history dynamic list.
 */
class ListViewHolder(
    private val binding: HistoryListItemBinding,
    private val onItemClick: (WorkoutNote) -> Unit
) : ListAdapter.ViewHolder<WorkoutNote>(binding) {

    companion object {
        operator fun invoke(parent: ViewGroup, onItemClick: (WorkoutNote) -> Unit) =
            ListViewHolder(
                HistoryListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), onItemClick
            )
    }

    override fun bind(item: WorkoutNote) {
        binding.noteTitle.text = item.title
        binding.noteCreatedAt.text = item.createdAt
        itemView.setOnClickListener { onItemClick(item) }
    }
}
