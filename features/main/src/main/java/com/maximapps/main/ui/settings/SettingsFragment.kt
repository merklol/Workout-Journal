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

package com.maximapps.main.ui.settings

import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.maximapps.main.BuildConfig
import com.maximapps.main.R
import com.maximapps.main.ext.openLink
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment(private val versionName: String) : PreferenceFragmentCompat(),
    Preference.OnPreferenceClickListener {
    private val versionPreference: Preference? by lazy {
        findPreference(getString(R.string.version_key))
    }

    private val rateAppPreference: Preference? by lazy {
        findPreference(getString(R.string.rate_app_key))
    }

    private val privacyPolicyPreference: Preference? by lazy {
        findPreference(getString(R.string.privacy_policy_key))
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        versionPreference?.let { it.summary = versionName }
        versionPreference?.onPreferenceClickListener = this
        rateAppPreference?.onPreferenceClickListener = this
        privacyPolicyPreference?.onPreferenceClickListener = this
    }

    override fun onPreferenceClick(preference: Preference) = when (preference.key) {
        getString(R.string.rate_app_key) -> {
            openLink(BuildConfig.GOOGLE_PLAY_URL)
            true
        }
        getString(R.string.privacy_policy_key) -> {
            openLink(BuildConfig.PRIVACY_POLICY_URL)
            true
        }
        getString(R.string.version_key) -> {
            Toast.makeText(requireContext(), versionName, Toast.LENGTH_SHORT).show()
            true
        }
        else -> false
    }
}
