package com.amoschoojs.livinginchrist

import android.os.Bundle
import android.widget.Toast
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        val checkBoxPreference = findPreference<CheckBoxPreference>("votdpreference")
        checkBoxPreference?.setOnPreferenceChangeListener { preference, newValue ->
            Toast.makeText(
                activity,
                "Please restart the app for the changes to be applied",
                Toast.LENGTH_SHORT
            ).show()
            true
        }


    }
}