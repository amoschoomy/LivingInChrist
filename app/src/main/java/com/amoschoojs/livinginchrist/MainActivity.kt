package com.amoschoojs.livinginchrist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var mainDrawer : DrawerLayout
    private  var toggleStatus: Boolean = false
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
        cardViewListener()
    }

    private fun cardViewListener() {
       val counterCard: CardView = findViewById(R.id.countercard)
        val planCard: CardView = findViewById(R.id.plancard)
        val quizCard :CardView = findViewById(R.id.quizcard)
        val studyCard : CardView = findViewById(R.id.studycard)
        val fragmentManager: FragmentManager= supportFragmentManager
        counterCard.setOnClickListener{
            fragmentManager.beginTransaction().replace(R.id.frag1,BlankFragment()).addToBackStack("counter").commit()
        }
        planCard.setOnClickListener{
            fragmentManager.beginTransaction().replace(R.id.frag1,PlanFragment()).addToBackStack("plans").commit()
        }
        quizCard.setOnClickListener{
            fragmentManager.beginTransaction().replace(R.id.frag1,QuizFragment()).addToBackStack("quizzes").commit()
        }
        studyCard.setOnClickListener{
            fragmentManager.beginTransaction().replace(R.id.frag1,BibleStudyFragment()).addToBackStack("bibstudy").commit()
        }



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
