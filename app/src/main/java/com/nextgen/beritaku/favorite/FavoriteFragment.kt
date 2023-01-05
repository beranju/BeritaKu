package com.nextgen.beritaku.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.data.source.Resource
import com.nextgen.beritaku.core.ui.NewsAdapter
import com.nextgen.beritaku.databinding.FragmentFavoriteBinding
import com.nextgen.beritaku.detail.DetailFragment
import com.nextgen.beritaku.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private val binding: FragmentFavoriteBinding by lazy {
        FragmentFavoriteBinding.inflate(layoutInflater, container, false)
    }
    private val viewModel: FavoriteViewModel by viewModels()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        binding.tvAktifkanFeature.setOnClickListener {
            try {
                moveToFavoriteActivity()
            }catch (e: Exception){
                Toast.makeText(requireContext(), "Module Not Found", Toast.LENGTH_SHORT).show()
            }

        }


        setupRecyclerView()

    }

    private fun moveToFavoriteActivity() {
        startActivity(Intent(requireActivity(), Class.forName("com.nextgen.beritaku.favorite.FavoriteActivity")))
    }

    private fun setupRecyclerView() {
        newsAdapter.viewType = 2
        viewModel.favoriteNews.observe(viewLifecycleOwner){result->
            newsAdapter.setData(result)
            newsAdapter.onClick = {selectedItem->
                val bundle = Bundle()
                bundle.putParcelable(DetailFragment.DATA_ITEM, selectedItem)
                findNavController().navigate(R.id.action_favorite_navigation_to_detailFragment, bundle)
            }
        }
        binding.rvFavoriteNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    private fun setupToolbar() {
        binding.apply {
            toolbar.title = "Favorite News"
            toolbarLayout.setExpandedTitleColor(resources.getColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        return binding.root
    }

    companion object {
        const val TAG = "FavoriteFragment"
    }
}