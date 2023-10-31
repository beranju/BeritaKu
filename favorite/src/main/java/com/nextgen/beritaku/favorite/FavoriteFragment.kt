package com.nextgen.beritaku.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.beritaku.core.ui.ForYouAdapter
import com.nextgen.beritaku.favorite.databinding.FragmentFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModel()
    private val newsAdapter: ForYouAdapter by lazy { ForYouAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(favoriteModule)

        binding.rvFavoriteNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = newsAdapter
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            binding.llError.visibility = if (error != null) View.VISIBLE else View.GONE
        }
        viewModel.news.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.sflNews.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.rvFavoriteNews.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
        binding.emptyFavorite.btnFindNews.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("beritaku://explore"))
            startActivity(intent)
        }

        binding.toolbar.ivNavigateBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        newsAdapter.onClick = { item ->
            val action = FavoriteFragmentDirections.actionFavoriteToDetail(item)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}