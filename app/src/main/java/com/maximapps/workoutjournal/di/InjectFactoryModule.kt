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

import androidx.fragment.app.Fragment
import com.maximapps.main.ui.MainFragment
import com.maximapps.timer.ui.TimerFragment
import com.maximapps.workoutNote.ui.details.DetailsFragment
import com.maximapps.workoutNote.ui.note.NoteFragment
import com.maximapps.workoutNote.ui.sessionDialog.SessionDialogFragment
import com.maximapps.core.di.FragmentKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
interface InjectFactoryModule {

    @Binds
    @IntoMap
    @FragmentKey(MainFragment::class)
    fun bindMainFragment(fragment: MainFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(DetailsFragment::class)
    fun bindDetailsFragment(fragment: DetailsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(NoteFragment::class)
    fun bindNoteFragment(fragment: NoteFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SessionDialogFragment::class)
    fun bindSessionDialogFragment(fragment: SessionDialogFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(TimerFragment::class)
    fun bindTimerFragment(fragment: TimerFragment): Fragment
}
