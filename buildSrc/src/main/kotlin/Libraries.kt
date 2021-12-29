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

/**
 * Current libraries' versions.
 */
object Versions {
    const val CoreKtx = "1.7.0"
    const val AppCompat = "1.4.0"
    const val Material = "1.4.0"
    const val Hilt = "2.40.1"
    const val Navigation = "2.4.0-rc01"
    const val Room = "2.4.0"
    const val Coroutines = "1.5.2"
    const val Preferences = "1.1.1"
    const val RecyclerView = "1.2.1"
    const val Lifecycle = "2.4.0"
    const val ViewBindingDelegate = "1.5.0-beta01"
    const val JUnit = "4.13.2"
    const val Espresso = "3.4.0"
    const val AndroidXJunit = "1.1.3"
}

object Libraries {
    /**
     * Provides extensions for Android's common libraries.
     */
    val coreKtx get() = "androidx.core:core-ktx:${Versions.CoreKtx}"

    /**
     * Backport of the Android 4.0 ActionBar API.
     */
    val appCompat get() = "androidx.appcompat:appcompat:${Versions.AppCompat}"

    /**
     * Material Components for Android.
     */
    val material get() = "com.google.android.material:material:${Versions.Material}"

    /**
     * Interactive settings screens.
     */
    val preferences get() = "androidx.preference:preference-ktx:${Versions.Preferences}"

    /**
     * Dynamic list for Android.
     */
    val recyclerview get() = "androidx.recyclerview:recyclerview:${Versions.RecyclerView}"

    /**
     * Lifecycle-aware components.
     */
    val lifecycle get() = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Lifecycle}"

    /**
     * Provides dependencies for the view binding delegate.
     */
    val viewBindingDelegate get() =
        "com.github.kirich1409:viewbindingpropertydelegate:${Versions.ViewBindingDelegate}"

    /**
     * Unit testing framework.
     */
    val jUnit get() = "junit:junit:${Versions.JUnit}"

    /**
     * Provides an extensive framework for testing Android apps.
     */
    val espresso get() = "androidx.test.espresso:espresso-core:${Versions.Espresso}"

    /**
     * Unit testing framework extensions for Android.
     */
    val androidXJunit get() = "androidx.test.ext:junit:${Versions.AndroidXJunit}"

    /**
     * Provides dependencies for the Room persistence library.
     */
    fun room() = listOf(
        "implementation" to "androidx.room:room-runtime:${Versions.Room}",
        "implementation" to "androidx.room:room-ktx:${Versions.Room}",
        "kapt" to "androidx.room:room-compiler:${Versions.Room}",
    )

    /**
     * Provides dependencies for the Navigation component.
     */
    fun navigation() = listOf(
        "implementation" to "androidx.navigation:navigation-fragment-ktx:${Versions.Navigation}",
        "implementation" to "androidx.navigation:navigation-ui-ktx:${Versions.Navigation}"
    )

    /**
     * Provides dependencies for a dependency injection framework.
     */
    fun daggerHilt() = listOf(
        "implementation" to "com.google.dagger:hilt-android:${Versions.Hilt}",
        "kapt" to "com.google.dagger:hilt-compiler:2.40.1"
    )

    /**
     * Provides dependencies for Kotlin coroutines.
     */
    fun coroutines() = listOf(
        "implementation" to
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Coroutines}",
        "implementation" to
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Coroutines}"
    )
}
