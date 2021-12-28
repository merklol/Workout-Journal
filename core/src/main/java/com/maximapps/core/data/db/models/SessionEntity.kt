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

package com.maximapps.core.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maximapps.core.domain.models.Session

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val noteId: Int,
    val name: String,
    val repetitions: String,
    val breakDuration: Long
)

/**
 * Maps the [SessionEntity] to the [Session].
 */
fun SessionEntity.toSession() = Session(
    id = id,
    name = name,
    repetitions = repetitions,
    breakDuration = breakDuration
)

/**
 * Maps the [Session] to the [SessionEntity].
 */
fun Session.toSessionEntity(noteId: Int) = SessionEntity(
    id = id,
    noteId = noteId,
    name = name,
    repetitions = repetitions,
    breakDuration = breakDuration
)
