package com.amoschoojs.livinginchrist

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Tab Adapter class
 */
class TabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2 //hardcoded value
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            CounterFragment()
        } else {
            HistoryFragment()
        }

    }


}