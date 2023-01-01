package com.nextgen.beritaku.explore

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.ui.NewsAdapter
import com.nextgen.beritaku.databinding.FragmentExploreBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExploreViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // * * https://medium.com/@abubakr.jabbarov/shared-searchview-with-tabslaout-and-collapsing-toolbar-in-android-daa19ee0fd6a
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        val newsAdapter = NewsAdapter()
        newsAdapter.viewType = 2


        viewModel.exploreNews("general", null, null).observe(viewLifecycleOwner){result->
            when(result){
                is Resource.Success -> {
                    newsAdapter.setData(result.data)
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }

//        binding.rvNewsCategory.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            setHasFixedSize(true)
//            adapter = newsAdapter
//        }

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
    }
}