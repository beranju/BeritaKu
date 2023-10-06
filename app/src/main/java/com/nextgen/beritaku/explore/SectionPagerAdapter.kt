package com.nextgen.beritaku.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nextgen.beritaku.utils.Categories

class SectionPagerAdapter(activity: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(activity, lifecycle) {
    override fun getItemCount(): Int = 13

    override fun createFragment(position: Int): Fragment {
        val fragment = NewsFragment()
        val bundle = Bundle()
        val selectedCategory = Categories.values()[position]
        bundle.putString(NewsFragment.ARG_SECTION_NUMBER, selectedCategory.value)
//        when(position){
//            0 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, Categories.NONE.value)
//            1 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, Categories.Top.value)
//            2 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, Categories.Bisnis.value)
//            3 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, Categories.Hiburan.value)
//            4 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, Categories.Lingkungan.value)
//            5 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, Categories.Makanan.value)
//            6 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, Categories.Kesehatan.value)
//            7 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, Categories.Politik.value)
//            8 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, Categories.Sains.value)
//            9 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, Categories.Olahraga.value)
//            10 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, Categories.Teknologi.value)
//            11 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, Categories.Wisata.value)
//            12 ->bundle.putString(NewsFragment.ARG_SECTION_NUMBER, Categories.Dunia.value)
//        }
        fragment.arguments = bundle
        return fragment
    }
}