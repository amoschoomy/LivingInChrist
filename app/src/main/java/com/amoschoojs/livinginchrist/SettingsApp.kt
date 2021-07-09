package com.amoschoojs.livinginchrist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SettingsApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, SettingsFragment())
            .commit()
    }
}