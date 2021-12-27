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

package com.maximapps.workoutjournal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.maximapps.core.di.setupFragmentFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostActivity : AppCompatActivity(R.layout.activity_host) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setupFragmentFactory()
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
        setupNavController()
    }

    private fun setupNavController() {
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(
            findNavController(),
            AppBarConfiguration.Builder(R.id.mainFragment, R.id.timerFragment).build()
        )
    }

    private fun findNavController() = (supportFragmentManager
        .findFragmentById(R.id.navHostFragment) as NavHostFragment)
        .navController
}
