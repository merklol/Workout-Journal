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

package com.maximapps.workoutjournal.di

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.app.NotificationCompat
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.maximapps.core.data.db.AppDatabase
import com.maximapps.core.di.DefaultSharedPreferences
import com.maximapps.core.di.UserInputSharedPreferences
import com.maximapps.core.utils.NotificationChannel.NOTIFICATION_CHANNEL_ID
import com.maximapps.core.utils.navigation.GlobalNavHost
import com.maximapps.workoutjournal.BuildConfig
import com.maximapps.workoutjournal.HostActivity
import com.maximapps.workoutjournal.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRoomDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "workout_notes")
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    @UserInputSharedPreferences
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("user-input-preferences", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    @DefaultSharedPreferences
    fun provideDefaultSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun provideGlobalNavHost() = GlobalNavHost(R.id.navHostFragment)

    @Provides
    @Singleton
    fun provideVersionName() = BuildConfig.VERSION_NAME

    @Provides
    @Singleton
    fun providePendingIntent(@ApplicationContext context: Context): PendingIntent =
        PendingIntent.getActivity(
            context,
            0,
            Intent(context, HostActivity::class.java).also {
                it.action = Intent.ACTION_MAIN
                it.addCategory(Intent.CATEGORY_LAUNCHER)
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

    @Provides
    @Singleton
    fun provideNotification(
        @ApplicationContext context: Context,
        pendingIntent: PendingIntent
    ): Notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setAutoCancel(false)
        .setOngoing(true)
        .setContentTitle(context.getString(R.string.notification_content_title))
        .setContentText(context.getString(R.string.notification_content_text))
        .setSmallIcon(R.drawable.ic_timer)
        .setContentIntent(pendingIntent)
        .build()
}
