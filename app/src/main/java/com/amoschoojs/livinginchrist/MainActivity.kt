package com.amoschoojs.livinginchrist

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ScrollView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var mainDrawer : DrawerLayout
    private  var toggleStatus: Boolean = false
    private lateinit var nightModeToggle: SwitchCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_drawer)
        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        mainDrawer=findViewById(R.id.maindrawer)

        setSupportActionBar(toolbar)
        val toggle =
            ActionBarDrawerToggle(this, mainDrawer, toolbar, R.string.open_menu, R.string.close_menu)
        mainDrawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.navview)
        navigationView.setNavigationItemSelectedListener(CustomNavigationViewListener(this))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        val appBarSwitch = menu?.findItem(R.id.app_bar_switch)
        val nightModeToggle : SwitchCompat=appBarSwitch?.actionView?.findViewById(R.id.nightmode) as SwitchCompat
        val sharedPreferences: SharedPreferences= getPreferences(0)
        toggleStatus=sharedPreferences.getBoolean("toggleNight",false)
        Log.e("TEST", toggleStatus.toString()+"ON OPTION")
        nightModeToggle.isChecked=toggleStatus
        nightModeToggle.setOnCheckedChangeListener { _, b ->
            if (b) {
                //open job.
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                toggleStatus=true
                Log.e("TEST",toggleStatus.toString())
            } else {
                //close job.
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                toggleStatus=false
            }
        }
        return true
    }

    override fun onStop() {
        val sharedPreferences:SharedPreferences.Editor= getPreferences(0).edit()
        sharedPreferences.putBoolean("toggleNight",toggleStatus)
        sharedPreferences.apply()
        super.onStop()
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}
