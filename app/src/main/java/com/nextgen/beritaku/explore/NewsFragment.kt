package com.nextgen.beritaku.explore

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.ui.ForYouAdapter
import com.nextgen.beritaku.core.ui.NewsAdapter
import com.nextgen.beritaku.databinding.FragmentNewsBinding
import com.nextgen.beritaku.detail.DetailFragment
import com.nextgen.beritaku.utils.Categories
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExploreViewModel by viewModel()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }
    private val exploreAdapter: ForYouAdapter by lazy {
        ForYouAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabName = arguments?.getString(ARG_SECTION_NUMBER).toString()

        if (tabName.isEmpty() || tabName == Categories.Semua.value) {
            viewModel.fetchNews(null, null)
        } else {
            viewModel.fetchNews(tabName)
        }

        binding.rvNewsItem.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = exploreAdapter
        }

        viewModel.news.observe(viewLifecycleOwner) { data ->
            exploreAdapter.submitList(data)
        }
        viewModel.loading.observe(viewLifecycleOwner){isLoading ->
            isLoading(isLoading)
        }
        viewModel.error.observe(viewLifecycleOwner){error ->
            Log.e(TAG, "onFailure: $error")
        }

    }

    private fun setupRecyclerView() {
        newsAdapter.viewType = 2
        binding.rvNewsItem.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = newsAdapter
        }
        newsAdapter.onClick = { dataNews ->
            val bundle = Bundle()
            bundle.putParcelable(DetailFragment.DATA_ITEM, dataNews)
            findNavController().navigate(R.id.action_explore_navigation_to_detailFragment, bundle)
        }
    }

    private fun isLoading(state: Boolean) {
        binding.loadData.visibility = if (state) View.VISIBLE else View.GONE
        binding.rvNewsItem.visibility = if (state) View.GONE else View.VISIBLE
    }

    private fun fetchData(tabName: String) {
//        viewModel.exploreNews(tabName, null).observe(viewLifecycleOwner){result->
//            when(result){
//                is Resource.Success -> {
//                    isLoading(false)
//                    binding.emptyDataNews.root.visibility = if (result.data!!.isEmpty()) View.VISIBLE else View.GONE
//                    newsAdapter.setData(result.data)
//                }
//                is Resource.Error -> {
//                    isLoading(false)
//                    binding.error.root.visibility = View.VISIBLE
//                    binding.error.tvEmpty.text = result.message
//                    Log.e(TAG, "${result.message}")
//                }
//                is Resource.Loading -> {
//                    isLoading(true)
//                }
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.rvNewsItem.adapter = null
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "NewsFragment"
        const val ARG_SECTION_NUMBER = "section_number"

    }
}