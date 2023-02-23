package com.nextgen.beritaku.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(activity, lifecycle) {
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        val fragment = NewsFragment()
        val bundle = Bundle()
        when(position){
            0 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, NewsFragment.TAB_BUSINESS)
            1 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, NewsFragment.TAB_ENTERTAINMENT)
            2 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, NewsFragment.TAB_HEALTH)
            3 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, NewsFragment.TAB_SCIENCE)
            4 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, NewsFragment.TAB_SPORT)
            5 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, NewsFragment.TAB_TECH)
        }
        fragment.arguments = bundle
        return fragment
    }
}