package com.nextgen.beritaku.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.ui.NewsAdapter
import com.nextgen.beritaku.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment  : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()
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

        setupToolbar()
        setupRecyclerView()
        fetchData()

    }

    private fun fetchData() {
        homeViewModel.headlineNews("general", null, 7).observe(viewLifecycleOwner){result->
            when(result){
                is Resource.Success -> {
                    isLoading(false)
                    newsAdapter.setData(result.data)
                    setTopNews(result.data?.randomOrNull())
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

    private fun isLoading(state: Boolean) {
        binding.pbMain.apply {
            visibility = if (state) View.VISIBLE else View.GONE
        }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            (activity as AppCompatActivity).setSupportActionBar(this)
            (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

            inflateMenu(R.menu.menu_option_home)
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.explore_menu -> {
                        findNavController().navigate(R.id.explore_navigation)
                        true
                    }
                    R.id.favorite_menu -> {
                        findNavController().navigate(R.id.favorite_navigation)
                        true
                    }
                    R.id.profile_menu -> {
                        findNavController().navigate(R.id.account_navigation)
                        true
                    }
                    else -> super.onOptionsItemSelected(it)
                }
            }

        }

    }

    private fun setupRecyclerView() {
        newsAdapter.viewType = 1
        binding.rvHeadline.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

    }

    private fun setTopNews(random: NewsModel?) {
        binding.apply {
            tvTitleNews.text = random?.title
            tvLabel.text = random?.source?.name
            Glide.with(requireContext())
                .load(random?.urlToImage)
                .centerCrop()
                .into(thumbnailNews)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val TAG = "HomeFragment"
    }
}