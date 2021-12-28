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

import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.maximapps.core.ui.models.ListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Implementation of [RecyclerView.Adapter] for list.
 */
abstract class ListAdapter<T> : RecyclerView.Adapter<ListAdapter.ViewHolder<T>>() {
    protected val mutableState = MutableStateFlow<ListState<T>>(ListState())
    val state: StateFlow<ListState<T>> = mutableState

    abstract fun getItemAt(position: Int): T

    abstract fun setState(data: ListState<T>)

    abstract fun subscribeToDataChanges(onChanged: (Int) -> Unit)

    abstract fun unsubscribeFromDataChanges()

    abstract class ViewHolder<T>(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: T)
    }
}

/**
 * Returns a list adapter containing only the specified object.
 */
fun <T> listAdapterOf(init: (parent: ViewGroup) -> ListAdapter.ViewHolder<T>) =
    object : ListAdapter<T>() {
        private val observer: ListAdapterDataObserver = ListAdapterDataObserver()

        override fun setState(data: ListState<T>) {
            val oldItems = state.value.items
            val result = DiffUtil.calculateDiff(ListDiffUtil(oldItems, data.items))
            mutableState.value = data
            result.dispatchUpdatesTo(this)
        }

        override fun getItemCount() = state.value.items.size

        override fun getItemAt(position: Int) = state.value.items[position]

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = init(parent)

        override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
            holder.bind(state.value.items[position])
        }

        override fun subscribeToDataChanges(onChanged: (Int) -> Unit) {
            observer.setOnChangedListener(onChanged)
            registerAdapterDataObserver(observer)
        }

        override fun unsubscribeFromDataChanges() = unregisterAdapterDataObserver(observer)

        inner class ListAdapterDataObserver : RecyclerView.AdapterDataObserver() {
            private lateinit var listener: (Int) -> Unit

            fun setOnChangedListener(listener: (Int) -> Unit) {
                this.listener = listener
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                listener(positionStart)
            }
        }
    }

/**
 * Returns a localized formatted string from the application's package's default string table.
 */
fun <T> ListAdapter.ViewHolder<T>.getString(@StringRes resId: Int, vararg formatArgs: Any) =
    itemView.context.getString(resId, *formatArgs)
