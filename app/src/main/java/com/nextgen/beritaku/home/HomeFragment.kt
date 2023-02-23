package com.nextgen.beritaku.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.ui.NewsAdapter
import com.nextgen.beritaku.databinding.FragmentHomeBinding
import com.nextgen.beritaku.detail.DetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment  : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        fetchData()

        homeViewModel.topNews.observe(viewLifecycleOwner){
            setTopNews(it)
        }
        binding.tvViewMore.setOnClickListener {
            findNavController().navigate(R.id.explore_navigation)
        }
    }

    private fun fetchData() {
        homeViewModel.headlineNews().observe(viewLifecycleOwner){result->
            when(result){
                is Resource.Success -> {
                    isLoading(false)
                    newsAdapter.setData(result.data)
                    result.data?.map {
                        newsAdapter.onClick = {selectedNews->
                            val bundle = Bundle()
                            bundle.putParcelable(DetailFragment.DATA_ITEM, selectedNews)
                            findNavController().navigate(R.id.action_home_navigation_to_detailFragment, bundle)
                        }
                    }
                }
                is Resource.Error -> {
                    isLoading(false)
                    binding.error.root.visibility = View.VISIBLE
                    binding.error.tvEmpty.text = result.message
                    binding.container.visibility = View.GONE
                    Log.e(TAG, "${result.message}")
                }
                is Resource.Loading -> {
                    isLoading(true)
                }
            }
        }
    }

    private fun isLoading(state: Boolean) {
        binding.pbMain.visibility = if (state) View.VISIBLE else View.GONE
        binding.rvHeadline.visibility = if (state) View.GONE else View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter.viewType = 1
        binding.rvHeadline.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

    }

    private fun setTopNews(random: NewsModel) {
        binding.apply {
            tvTitleNews.text = random.title
            tvLabel.text = random.source.name
            Glide.with(requireContext())
                .load(random.urlToImage)
                .centerCrop()
                .into(thumbnailNews)

            tvReadmore.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(DetailFragment.DATA_ITEM, random)
                findNavController().navigate(R.id.action_home_navigation_to_detailFragment, bundle)
            }
        }

    }

    override fun onDestroyView() {
        binding.rvHeadline.adapter = null
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val TAG = "HomeFragment"
    }
}