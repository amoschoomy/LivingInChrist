package com.amoschoojs.livinginchrist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private lateinit var mainDrawer : DrawerLayout
    private  var toggleStatus: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_drawer)
        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        mainDrawer=findViewById(R.id.maindrawer)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, mainDrawer, toolbar, R.string.open_menu, R.string.close_menu)
        mainDrawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.navview)
        navigationView.setNavigationItemSelectedListener(CustomNavigationViewListener(this))
        cardViewListener()

        if (!getSharedPreferences("abc,",0).contains("history")){
            val gson = Gson()
            val arrayString=gson.toJson(ArrayList<Int>())
            getSharedPreferences("abc",0).edit().putString("history",arrayString).apply()
        }


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
        return true
    }

    override fun onStop() {
        val sharedPreferences:SharedPreferences.Editor= getPreferences(0).edit()
        sharedPreferences.putBoolean("toggleNight",toggleStatus)
        sharedPreferences.apply()
        super.onStop()
    }

}
