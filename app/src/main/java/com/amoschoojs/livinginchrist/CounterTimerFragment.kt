package com.amoschoojs.livinginchrist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class CounterTimerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabAdapter = TabAdapter(this)
        val tabLayout: TabLayout = view.findViewById(R.id.tabbar)
        val viewPager: ViewPager2 = view.findViewById(R.id.viewPager2)
        viewPager.adapter = tabAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            viewPager.setCurrentItem(tab.position, true)
            when (position) {
                0 -> tab.text = "Counter"
                1 -> tab.text = "History"
            }
        }.attach()
    }


}