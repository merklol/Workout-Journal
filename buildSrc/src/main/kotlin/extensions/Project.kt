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

@file:Suppress("UnstableApiUsage")

package extensions

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Specifies defaults for application id and  test instrumentation runner.
 */
fun Project.configuration(applicationId: String, testInstrumentationRunner: String) =
    configure<BaseAppModuleExtension> {
        defaultConfig {
            this.applicationId = applicationId
            this.testInstrumentationRunner = testInstrumentationRunner
        }
    }

/**
 * Specifies defaults for test instrumentation runner.
 */
fun Project.configuration(testInstrumentationRunner: String) =
    configure<LibraryExtension> {
        defaultConfig {
            this.testInstrumentationRunner = testInstrumentationRunner
        }
    }

/**
 * Specifies defaults for test instrumentation runner and adds fields for
 * the generated BuildConfig.
 */
fun Project.configuration(testInstrumentationRunner: String, configFields: List<ConfigField>) =
    configure<LibraryExtension> {
        defaultConfig {
            this.testInstrumentationRunner = testInstrumentationRunner
        }
        buildTypes {
            debug {
                configFields.forEach { buildConfigField(it.type, it.name, it.value) }
            }
            release {
                configFields.forEach { buildConfigField(it.type, it.name, it.value) }
            }
        }
    }
