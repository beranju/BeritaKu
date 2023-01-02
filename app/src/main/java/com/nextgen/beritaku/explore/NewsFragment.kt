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

        fetchData(tabName)
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        newsAdapter.viewType = 2
        binding.rvNewsItem.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    private fun isLoading(state: Boolean) {
        binding.loadData.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun fetchData(tabName: String) {
        viewModel.exploreNews(tabName, null).observe(viewLifecycleOwner){result->
            when(result){
                is Resource.Success -> {
                    isLoading(false)
                    newsAdapter.setData(result.data)
                }
                is Resource.Error -> {
                    isLoading(false)
                    binding.emptyData.visibility = View.VISIBLE
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