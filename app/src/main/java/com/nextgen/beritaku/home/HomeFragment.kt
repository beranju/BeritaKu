package com.nextgen.beritaku.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.ui.ForYouAdapter
import com.nextgen.beritaku.core.ui.HeadlineAdapter
import com.nextgen.beritaku.core.utils.Utils.getCurrentDayDate
import com.nextgen.beritaku.databinding.FragmentHomeBinding
import com.nextgen.beritaku.detail.DetailFragment
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
        val currentDayDate = getCurrentDayDate()
        binding.tvDateToday.text = currentDayDate
        binding.tvGreeting.text = if (homeViewModel.userData == null) {
            "Welcome,\nTemukan berita mu"
        } else {
            buildString {
                append("Welcome, ")
                append(homeViewModel.userData?.displayName ?: "")
            }
        }

        forYouAdapter.onClick = { item ->
            val bundle = Bundle()
            bundle.putParcelable(DetailFragment.DATA_ITEM, item)
            findNavController().navigate(R.id.action_home_navigation_to_detailFragment, bundle)
        }
        headlineAdapter.onClick = { item ->
            val bundle = Bundle()
            bundle.putParcelable(DetailFragment.DATA_ITEM, item)
            findNavController().navigate(R.id.action_home_navigation_to_detailFragment, bundle)
        }

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
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvHomeHeadline)

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

        binding.srlHome.setOnRefreshListener {
            homeViewModel.topHeadline()
        }
        binding.tvForYouViewAll.setOnClickListener(this)
        binding.appBar.sivProfile.setOnClickListener(this)
        binding.appBar.ivFavorite.setOnClickListener(this)
    }

    private fun showError(message: String) {
        binding.error.root.visibility = View.VISIBLE
        binding.error.tvEmpty.text = message
    }

    private fun isLoading(state: Boolean) {
        binding.sflForYou.visibility = if (state) View.VISIBLE else View.GONE
        binding.sflHeadline.visibility = if (state) View.VISIBLE else View.GONE
        binding.rvHomeForYou.visibility = if (state) View.GONE else View.VISIBLE
        binding.rvHomeHeadline.visibility = if (state) View.GONE else View.VISIBLE
        if(state){
            binding.srlHome.isRefreshing = false
        }
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

            binding.appBar.ivFavorite -> {
                try {
                    installModuleFavorite()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Module Not Found", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            else -> {
                Toast.makeText(requireContext(), "No action", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun installModuleFavorite() {
        val splitInstallManager = SplitInstallManagerFactory.create(requireContext())
        val moduleFavorite = "favorite"
        if (splitInstallManager.installedModules.contains(moduleFavorite)) {
            moveToActivity()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleFavorite)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    moveToActivity()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Error installing module", Toast.LENGTH_SHORT)
                        .show()
                }
        }

    }

    private fun moveToActivity() {
        startActivity(
            Intent(
                requireContext(),
                Class.forName("com.nextgen.beritaku.favorite.FavoriteActivity")
            )
        )
    }
}