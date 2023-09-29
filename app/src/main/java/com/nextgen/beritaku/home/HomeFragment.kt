package com.nextgen.beritaku.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.carousel.CarouselLayoutManager
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.ui.ForYouAdapter
import com.nextgen.beritaku.core.ui.HeadlineAdapter
import com.nextgen.beritaku.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(), View.OnClickListener {
    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val forYouAdapter: ForYouAdapter by lazy {
        ForYouAdapter()
    }
    private val headlineAdapter: HeadlineAdapter by lazy {
        HeadlineAdapter()
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

        binding.rvHomeForYou.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = forYouAdapter
        }
        binding.rvHomeHeadline.apply {
            layoutManager = CarouselLayoutManager()
            setHasFixedSize(true)
            adapter = headlineAdapter
        }

        homeViewModel.forYouNews.observe(viewLifecycleOwner) { items ->
            forYouAdapter.submitList(items)
        }
        homeViewModel.topNews.observe(viewLifecycleOwner) { items ->
            headlineAdapter.submitList(items)
        }
        homeViewModel.error.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) showError(it)
        }
        homeViewModel.loading.observe(viewLifecycleOwner) {
            isLoading(it)
        }

        binding.tvForYouViewAll.setOnClickListener(this)
        binding.appBar.sivProfile.setOnClickListener(this)
    }

    private fun showError(message: String) {
        binding.error.root.visibility = View.VISIBLE
        binding.error.tvEmpty.text = message
    }

    private fun isLoading(state: Boolean) {
        binding.sflForYou.visibility = if (state) View.VISIBLE else View.GONE
        binding.sflHeadline.visibility = if (state) View.VISIBLE else View.GONE
        binding.rvHomeForYou.visibility = if (state) View.GONE else View.VISIBLE
    }

    companion object {
        const val TAG = "HomeFragment"
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.tvForYouViewAll -> {
                findNavController().navigate(R.id.explore_navigation)
            }

            binding.appBar.sivProfile -> {
                findNavController().navigate(R.id.account_navigation)
            }

            else -> {
                Toast.makeText(requireContext(), "No action", Toast.LENGTH_SHORT).show()
            }
        }
    }
}