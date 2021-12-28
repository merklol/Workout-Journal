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

@file:Suppress("unused")

package com.maximapps.workoutjournal.di

import com.maximapps.core.data.repositories.DefaultCrudRepository
import com.maximapps.core.domain.CrudRepository
import com.maximapps.core.utils.navigation.GlobalDirections
import com.maximapps.timer.data.repositories.DefaultTimerRepository
import com.maximapps.timer.domain.repositories.TimerRepository
import com.maximapps.workoutNote.data.SharedPrefUserInputStorage
import com.maximapps.workoutNote.domain.storage.UserInputStorage
import com.maximapps.workoutjournal.navigation.AppDirections
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CoreModule {
    @Binds
    @Singleton
    fun bindGlobalDirections(appDirections: AppDirections): GlobalDirections

    @Binds
    @Singleton
    fun bindCrudRepository(repository: DefaultCrudRepository): CrudRepository

    @Binds
    fun bindTimerRepository(repository: DefaultTimerRepository): TimerRepository

    @Binds
    @Singleton
    fun bindUserInputStorage(userInputStorage: SharedPrefUserInputStorage): UserInputStorage
}
