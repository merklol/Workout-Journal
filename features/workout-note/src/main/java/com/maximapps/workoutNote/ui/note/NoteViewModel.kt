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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximapps.core.domain.models.Session
import com.maximapps.core.ui.StateOwner
import com.maximapps.core.ui.models.ListState
import com.maximapps.core.utils.navigation.models.Properties
import com.maximapps.workoutNote.domain.usecases.DeleteSessionUseCase
import com.maximapps.workoutNote.domain.usecases.FetchSessionsUseCase
import com.maximapps.workoutNote.domain.usecases.RestoreSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    fetchSessionsUseCase: FetchSessionsUseCase,
    private val deleteSessionUseCase: DeleteSessionUseCase,
    private val restoreSessionUseCase: RestoreSessionUseCase
) : ViewModel(), StateOwner<ListState<Session>> {
    private val noteId = savedStateHandle.get<Properties>("properties")?.id ?: 0

    override val state = fetchSessionsUseCase(noteId)
        .stateIn(viewModelScope, SharingStarted.Lazily, ListState())

    fun deleteSession(noteId: Int, session: Session) {
        viewModelScope.launch { deleteSessionUseCase(noteId, session) }
    }

    fun restoreSession(noteId: Int, session: Session) {
        viewModelScope.launch { restoreSessionUseCase(noteId, session) }
    }
}
