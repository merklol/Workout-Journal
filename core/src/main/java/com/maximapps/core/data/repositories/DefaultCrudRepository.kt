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

package com.maximapps.core.data.repositories

import com.maximapps.core.data.db.AppDatabase
import com.maximapps.core.data.db.models.SessionEntity
import com.maximapps.core.data.db.models.toSession
import com.maximapps.core.data.db.models.toSessionEntity
import com.maximapps.core.data.db.models.toWorkoutNote
import com.maximapps.core.data.db.models.toWorkoutNoteEntity
import com.maximapps.core.domain.models.FetchResult
import com.maximapps.core.domain.models.Session
import com.maximapps.core.domain.models.WorkoutNote
import com.maximapps.core.domain.CrudRepository
import com.maximapps.core.utils.execute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Default implementation of the [CrudRepository] interface.
 */
class DefaultCrudRepository @Inject constructor(
    database: AppDatabase
) : CrudRepository {
    private var cashed = listOf<SessionEntity>()
    private val sessionsDao = database.sessionsDao()
    private val workoutNotesDao = database.workoutNotesDao()

    override fun fetchWorkoutNotes() = workoutNotesDao.fetchWorkoutNotes()
        .map { list ->
            when {
                list.isEmpty() -> FetchResult.Empty
                else -> FetchResult.Result(list.map { it.toWorkoutNote() })
            }
        }

    override fun fetchSessionsByNoteId(noteId: Int) = sessionsDao.fetchSessions(noteId)
        .map { list ->
            when {
                list.isEmpty() -> FetchResult.Empty
                else -> FetchResult.Result(list.map { it.toSession() })
            }
        }

    override suspend fun deleteWorkoutNote(item: WorkoutNote) = execute {
        cashed = sessionsDao.fetchSessionsById(item.id)
        workoutNotesDao.removeWorkoutNotes(item.toWorkoutNoteEntity())
        sessionsDao.removeSessions(item.id)
    }

    override suspend fun addWorkoutNote(item: WorkoutNote) = withContext(Dispatchers.IO) {
        val id = workoutNotesDao.saveWorkoutNote(item.toWorkoutNoteEntity())
        val note = workoutNotesDao.fetchWorkoutNoteById(id.toInt())
        WorkoutNote(note.id, note.title, note.createdAt)
    }

    override suspend fun restoreWorkoutNote(item: WorkoutNote) = execute {
        sessionsDao.saveSessions(cashed)
        workoutNotesDao.saveWorkoutNote(item.toWorkoutNoteEntity())
    }

    override suspend fun deleteSessionByNoteId(noteId: Int, item: Session) = execute {
        sessionsDao.removeSession(item.toSessionEntity(noteId))
    }

    override suspend fun restoreSessionByNoteId(noteId: Int, item: Session) = execute {
        sessionsDao.saveSession(item.toSessionEntity(noteId))
    }

    override suspend fun renameWorkoutNote(id: Int, title: String) = execute {
        workoutNotesDao.updateWorkoutNote(id, title)
    }

    override suspend fun saveSession(noteId: Int, item: Session) = execute {
        sessionsDao.saveSession(item.toSessionEntity(noteId))
    }
}
