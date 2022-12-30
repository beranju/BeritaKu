package com.nextgen.beritaku.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.data.source.NewsRepository
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.data.source.remote.RemoteDataSource
import com.nextgen.beritaku.core.data.source.remote.network.ApiService
import com.nextgen.beritaku.core.ui.HeadlineAdapter
import com.nextgen.beritaku.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class HomeFragment  : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater, container, false)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsAdapter = HeadlineAdapter()
        val category = "general"
        val query = ""

        homeViewModel.headlineNews(category, query).observe(viewLifecycleOwner){result->
            when(result){
                is Resource.Success -> {
                    newsAdapter.setData(result.data)
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






    companion object {
    }
}