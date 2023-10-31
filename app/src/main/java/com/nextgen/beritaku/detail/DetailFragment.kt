package com.nextgen.beritaku.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.utils.Utils
import com.nextgen.beritaku.databinding.FragmentDetailBinding
import com.nextgen.beritaku.utils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModel()
    private var dataNews: NewsDataItem? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataNews = DetailFragmentArgs.fromBundle(arguments as Bundle).dataItem
        Log.d("DetailFragment", "data: $dataNews")

        viewModel.isFavoriteNews(dataNews!!.articleId.orEmpty())
        if (dataNews != null) setupView(dataNews)

        binding.ivNavigateUp.setOnClickListener(this)
        binding.ivShare.setOnClickListener(this)
        binding.btnReadMore.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(WebFragment.URL, dataNews?.link)
            findNavController().navigate(R.id.action_detailFragment_to_webFragment, bundle)
        }

    }

    private fun setupView(data: NewsDataItem?) {
        binding.sivThumbnail.loadImage(data?.imageUrl.orEmpty())
        binding.apply {
            tvTitle.text = data?.title
            tvAuthor.text = if (data?.creator.equals("null")) data?.sourceId else data?.creator
            tvCategory.text = data?.category?.first() ?: "Semua"
            tvDescription.text = data?.description
            tvContent.text = data?.content
            tvDate.text = Utils.calculateReadTime(data?.pubDate.orEmpty())
            ivFavorite.setOnClickListener {
                viewModel.setFavoriteNews(dataNews!!)
            }
            viewModel.isFavorite.observe(viewLifecycleOwner) {
                setStatusFavorite(it!!)
            }
        }

    }

    private fun setStatusFavorite(favorite: Boolean) {
        if (favorite) {
            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_favorite_24
                )
            )
        } else {
            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_favorite_border_24
                )
            )
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.ivNavigateUp -> {
                findNavController().navigateUp()
            }

            binding.ivShare -> {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_SUBJECT, "Baca berita menarik dari ${dataNews?.sourceId}")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        dataNews?.title + "\nKlik link dibawah untuk selengkapnya ${dataNews?.link}"
                    )
                    type = "text/plain"
                }
                val shareNews = Intent.createChooser(intent, null)
                startActivity(shareNews)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DATA_ITEM = "data"
    }

}