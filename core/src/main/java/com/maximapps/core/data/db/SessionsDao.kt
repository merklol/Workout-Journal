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

package com.maximapps.core.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maximapps.core.data.db.models.SessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionsDao {
    @Query("SELECT * FROM sessions WHERE noteId =:noteId")
    fun fetchSessions(noteId: Int): Flow<List<SessionEntity>>

    @Query("SELECT * FROM sessions WHERE noteId =:noteId")
    fun fetchSessionsById(noteId: Int): List<SessionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSession(sessionEntity: SessionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSessions(sessionEntity: List<SessionEntity>)

    @Delete
    suspend fun removeSession(sessionEntity: SessionEntity)

    @Query("DELETE FROM sessions WHERE noteID = :noteId")
    suspend fun removeSessions(noteId: Int)
}
