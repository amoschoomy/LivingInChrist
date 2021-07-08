package com.amoschoojs.livinginchrist

import android.app.Activity
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class CustomNavigationViewListener(private val activity: Activity) : NavigationView.OnNavigationItemSelectedListener {


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.counter->Log.e("TEST","Clicked conter")
            R.id.plans -> Log.e("TEST","CLICKED PLANS")
            R.id.quiz -> Log.e("TEST","Clicked quiz")
            R.id.study -> Log.e("TEST","Clicked stduy")

        }
        val drawer:DrawerLayout =activity.findViewById<DrawerLayout>(R.id.maindrawer)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}