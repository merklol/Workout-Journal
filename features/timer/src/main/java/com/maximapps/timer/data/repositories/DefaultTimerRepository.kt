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

package com.maximapps.timer.data.repositories

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.maximapps.timer.data.services.TimerService
import com.maximapps.timer.data.services.TimerService.Companion.START_SHORT_BREAK
import com.maximapps.timer.data.services.TimerService.Companion.STOP_SHORT_BREAK
import com.maximapps.timer.domain.repositories.TimerRepository
import com.maximapps.timer.ext.sendCommandToService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * Default implementation of the [TimerRepository].
 */
class DefaultTimerRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : TimerRepository {
    private val timerState = MutableStateFlow<StateFlow<Long>?>(null)

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            timerState.value = (service as TimerService.TimerBinder).service.timerState
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            timerState.value = null
        }
    }

    override suspend fun subscribe(onTick: (Long) -> Unit) {
        timerState.collectLatest { it?.collectLatest(onTick) }
    }

    override fun startService(breakDuration: Long) {
        with(Intent(context, TimerService::class.java)) {
            val extra = TimerService.TIMER_DURATION_IN_MILLIS to breakDuration
            context.sendCommandToService(this, START_SHORT_BREAK, extra)
            context.bindService(this, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun stopService() {
        context.sendCommandToService(Intent(context, TimerService::class.java), STOP_SHORT_BREAK)
        context.unbindService(connection)
    }
}
