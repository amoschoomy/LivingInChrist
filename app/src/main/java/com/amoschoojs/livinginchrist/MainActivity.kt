package com.amoschoojs.livinginchrist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        val appBarSwitch = menu?.findItem(R.id.app_bar_switch)
        val scrollView: ScrollView = findViewById(R.id.scrollview)
        val nightModeToggle : SwitchCompat=appBarSwitch?.actionView?.findViewById(R.id.nightmode) as SwitchCompat
        nightModeToggle.setOnClickListener {
            when (nightModeToggle.isChecked) {
                true -> {
                    scrollView.setBackgroundColor(
                        ContextCompat.getColor(
                            applicationContext,
                            R.color.black
                        )
                    )
                }
                false -> scrollView.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.white
                    )
                )

            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}