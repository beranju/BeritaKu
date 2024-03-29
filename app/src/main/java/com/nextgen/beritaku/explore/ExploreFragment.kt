package com.nextgen.beritaku.explore

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.nextgen.beritaku.core.ui.ForYouAdapter
import com.nextgen.beritaku.databinding.FragmentExploreBinding
import com.nextgen.beritaku.utils.Categories
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreFragment : Fragment() {
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()
    private val searchAdapter: ForYouAdapter by lazy {
        ForYouAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()

        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchView.editText.setOnEditorActionListener { _, actionId, _ ->
            binding.searchBar.text = binding.searchView.text
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchView.text.toString()
                if (query.isNotEmpty()) {
                    viewModel.findNewsByQuery(query)
                } else {
                    Toast.makeText(requireContext(), "Masukkan beberapa kata", Toast.LENGTH_SHORT)
                        .show()
                }
                true
            } else {
                false
            }
        }

        searchAdapter.onClick = { item ->
            binding.searchView.hide()
            val action = ExploreFragmentDirections.actionExploreNavigationToDetailFragment(item)
            findNavController().navigate(action)
        }
        binding.rvItemSearch.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = searchAdapter
        }
        viewModel.news.observe(viewLifecycleOwner) { data ->
            searchAdapter.submitList(data)
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading(isLoading)
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Log.e(TAG, "onFailure: $error")
        }

    }

    private fun setupTabLayout() {
        binding.viewPager.adapter =
            SectionPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = Categories.values()[position].name
        }.attach()
    }


    private fun isLoading(state: Boolean) {
        binding.loadData.root.visibility = if (state) View.VISIBLE else View.GONE
        binding.rvItemSearch.visibility = if (state) View.GONE else View.VISIBLE
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