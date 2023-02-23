package com.nextgen.beritaku.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.nextgen.beritaku.R
import com.nextgen.beritaku.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupTabLayout()

    }

    private fun setupTabLayout() {
        binding.viewPager.adapter = SectionPagerAdapter( childFragmentManager, viewLifecycleOwner.lifecycle)
        TabLayoutMediator(binding.tabs, binding.viewPager){tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setupToolbar() {
        binding.searchButton.setOnClickListener {
            findNavController().navigate(R.id.action_explore_navigation_to_searchFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3,
            R.string.tab_text_4,
            R.string.tab_text_5,
            R.string.tab_text_6
        )
    }
}