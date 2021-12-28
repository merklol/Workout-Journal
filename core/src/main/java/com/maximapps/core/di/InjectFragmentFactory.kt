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

package com.maximapps.core.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject
import javax.inject.Provider

/**
 * Entry point for Hilt's Fragment factory.
 */
@EntryPoint
@InstallIn(ActivityComponent::class)
interface InjectFragmentFactoryEntryPoint {
    fun getFragmentFactory(): InjectFragmentFactory
}

/**
 * Hilt implementation of the Fragment factory interface.
 */
class InjectFragmentFactory @Inject constructor(
    private val providers: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)
        val creator = providers[fragmentClass] ?: providers.entries.firstOrNull {
            fragmentClass.isAssignableFrom(it.key)
        }?.value
        return creator?.get() ?: super.instantiate(classLoader, className)
    }
}

/**
 * Sets up an Activity for use with a Fragment factory.
 */
fun AppCompatActivity.setupFragmentFactory() {
    val entryPoint = EntryPointAccessors.fromActivity(
        this,
        InjectFragmentFactoryEntryPoint::class.java
    )
    supportFragmentManager.fragmentFactory = entryPoint.getFragmentFactory()
}
