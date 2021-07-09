package com.amoschoojs.livinginchrist

import android.app.Activity
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView

class CustomNavigationViewListener(
    private val activity: Activity,
    private val fragmentManager: FragmentManager
) : NavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.counter -> fragmentManager.beginTransaction()
                .replace(R.id.frag1, CounterTimerFragment()).addToBackStack("counter").commit()
            R.id.plans -> fragmentManager.beginTransaction().replace(R.id.frag1, PlanFragment())
                .addToBackStack("plans").commit()
            R.id.quiz -> fragmentManager.beginTransaction().replace(R.id.frag1, QuizFragment())
                .addToBackStack("quiz").commit()
            R.id.study -> fragmentManager.beginTransaction()
                .replace(R.id.frag1, BibleStudyFragment()).addToBackStack("counter").commit()

        }
        val drawer: DrawerLayout = activity.findViewById<DrawerLayout>(R.id.maindrawer)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}