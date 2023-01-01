package com.nextgen.beritaku.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.ui.NewsAdapter
import com.nextgen.beritaku.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment  : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsAdapter = NewsAdapter()
        newsAdapter.viewType = 1

        homeViewModel.headlineNews("general", null).observe(viewLifecycleOwner){result->
            when(result){
                is Resource.Success -> {
                    newsAdapter.setData(result.data)
                    setTopNews(result.data?.random())


                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
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
    }
}