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

package com.maximapps.main.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.maximapps.core.utils.navigation.GlobalDirections
import com.maximapps.core.utils.navigation.GlobalNavHost
import com.maximapps.main.R
import com.maximapps.main.databinding.FragmentMainBinding
import com.maximapps.main.ui.history.HistoryFragment
import com.maximapps.main.ui.pager.ViewPagerAdapter
import com.maximapps.main.ui.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment @Inject constructor(
    private val directions: GlobalDirections,
    private val host: GlobalNavHost,
    private val versionName: String
) : Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding()
    private val tabIcons = arrayOf(
        R.drawable.ic_baseline_bar_chart_24, R.drawable.ic_baseline_settings_24
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPagerAdapter(listOf(HistoryFragment(directions, host), SettingsFragment(versionName)))
        setupTabLayoutWithViewPager()
    }

    private fun setViewPagerAdapter(fragments: List<Fragment>) {
        binding.viewPager.adapter = ViewPagerAdapter(childFragmentManager, lifecycle, fragments)
    }

    private fun setupTabLayoutWithViewPager() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.icon = AppCompatResources.getDrawable(requireContext(), tabIcons[position])
        }.attach()
    }
}
