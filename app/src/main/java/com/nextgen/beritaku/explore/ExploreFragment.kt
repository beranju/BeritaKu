package com.nextgen.beritaku.explore

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.ui.NewsAdapter
import com.nextgen.beritaku.databinding.FragmentExploreBinding
import com.nextgen.beritaku.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExploreViewModel by viewModels()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }
    private val pagerAdapter: SectionPagerAdapter by lazy {
        SectionPagerAdapter(requireActivity() as AppCompatActivity)
    }
    private val newBinding: FragmentNewsBinding by lazy {
        FragmentNewsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        val viewPager: ViewPager2 = binding.viewPager as ViewPager2
        viewPager.adapter = pagerAdapter
        val tabs: TabLayout = binding.tabs as TabLayout
        TabLayoutMediator(tabs, viewPager){tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        newsAdapter.viewType = 2
        newBinding.rvNewsItem.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        binding.searchField.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun afterTextChanged(query: Editable?) {
                lifecycleScope.launch {
                    viewModel.exploreNews("general", query!!.trim().toString()).observe(viewLifecycleOwner){result->
                        when(result){
                            is Resource.Success -> {
                                isLoading(false)
                                newsAdapter.setData(result.data)
                            }
                            is Resource.Error -> {
                                isLoading(false)
                                Log.e(TAG, "${result.message}")
                            }
                            is Resource.Loading -> {
                                isLoading(true)
                            }
                        }
                    }
                }

            }

        })
    }

    private fun isLoading(loading: Boolean) {

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
        const val TAG = "ExploreFragment"
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