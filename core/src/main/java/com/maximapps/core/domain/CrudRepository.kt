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

package com.maximapps.core.domain

import com.maximapps.core.domain.models.FetchResult
import com.maximapps.core.domain.models.Session
import com.maximapps.core.domain.models.WorkoutNote
import kotlinx.coroutines.flow.Flow

/**
 * Repository to perform generic CRUD operations on a database.
 */
interface CrudRepository {

    /**
     * Fetches workout notes from the database.
     *
     * @return [Flow]
     */
    fun fetchWorkoutNotes(): Flow<FetchResult<WorkoutNote>>

    /**
     * Fetches sessions by workout note id from the database.
     *
     * @return [Flow]
     */
    fun fetchSessionsByNoteId(noteId: Int): Flow<FetchResult<Session>>

    /**
     * Deletes a workout note from the database.
     */
    suspend fun deleteWorkoutNote(item: WorkoutNote)

    /**
     * Deletes a session from the database.
     */
    suspend fun deleteSessionByNoteId(noteId: Int, item: Session)

    /**
     * Restores a workout note.
     */
    suspend fun restoreWorkoutNote(item: WorkoutNote)

    /**
     * Restores a session from the database.
     */
    suspend fun restoreSessionByNoteId(noteId: Int, item: Session)

    /**
     * Adds a workout note to the database.
     */
    suspend fun addWorkoutNote(item: WorkoutNote): WorkoutNote

    /**
     * Renames a workout note.
     */
    suspend fun renameWorkoutNote(id: Int, title: String)

    /**
     * Saves a session to the database.
     */
    suspend fun saveSession(noteId: Int, item: Session)
}
