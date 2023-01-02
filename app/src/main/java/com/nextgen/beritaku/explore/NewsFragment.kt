package com.nextgen.beritaku.explore

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.ui.NewsAdapter
import com.nextgen.beritaku.databinding.FragmentNewsBinding
import com.nextgen.beritaku.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExploreViewModel by viewModels()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabName = arguments?.getString(ARG_SECTION_NUMBER).toString()
        setViewPager(tabName)

        newsAdapter.viewType = 2

        binding.rvNewsItem.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = newsAdapter
        }



    }

    private fun isLoading(state: Boolean) {

    }

    private fun setViewPager(tabName: String) {
        when(tabName) {
            TAB_BUSINESS -> {
                viewModel.exploreNews("business",null, null).observe(viewLifecycleOwner){result->
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
            TAB_ENTERTAINMENT -> fetchData(tabName)
            TAB_HEALTH -> fetchData(tabName)
            TAB_SCIENCE -> fetchData(tabName)
            TAB_SPORT -> fetchData(tabName)
            TAB_TECH -> fetchData(tabName)
        }
    }

    private fun fetchData(tabName: String) {
        viewModel.exploreNews(tabName,null, null).observe(viewLifecycleOwner){result->
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = "NewsFragment"
        const val ARG_SECTION_NUMBER = "section_number"
        const val TAB_BUSINESS = "business"
        const val TAB_ENTERTAINMENT = "entertainment"
        const val TAB_HEALTH = "health"
        const val TAB_SCIENCE = "science"
        const val TAB_SPORT = "sport"
        const val TAB_TECH = "technology"

    }
}