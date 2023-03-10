package com.nextgen.beritaku.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.utils.DateUtils
import com.nextgen.beritaku.core.utils.loadImage
import com.nextgen.beritaku.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding
    private val viewModel: DetailViewModel by viewModel()
    private var dataNews: NewsModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataNews =
            activity?.intent?.getParcelableExtra(DATA_ITEM) ?: arguments?.getParcelable(DATA_ITEM)

        viewModel.isFavoriteNews(dataNews!!.publishedAt)
        setupView(dataNews)
        binding?.backButton?.setOnClickListener(this)
        binding?.ivShare?.setOnClickListener(this)

    }

    private fun setupView(data: NewsModel?) {
        data.let {
            binding?.apply {
                title.text = data?.title
                label.text = data?.source?.name ?: getString(R.string.text_anonim)
                description.text = data?.description
                author.text = data?.author ?: getString(R.string.text_anonim)
                date.text = DateUtils.dateFormat(data?.publishedAt.toString())
                content.text = data?.content.toString()
                thumbnail.loadImage(data?.urlToImage)
                readmore.setOnClickListener{
                    val bundle = Bundle()
                    bundle.putString(WebFragment.URL, data?.url)
                    findNavController().navigate(R.id.action_detailFragment_to_webFragment, bundle)
                }
                favorite.setOnClickListener {
                    viewModel.setFavoriteNews(dataNews!!)
                }
                viewModel.isFavorite.observe(viewLifecycleOwner){
                    if (it != null) setStatusFavorite(it)
                }
            }
        }

    }

    private fun setStatusFavorite(favorite: Boolean) {
        if (favorite){
            binding?.favorite?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_24))
        }else{
            binding?.favorite?.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_border_24))
        }
    }

    override fun onClick(view: View?) {
        when(view){
            binding?.backButton -> {
                findNavController().navigateUp()
            }
            binding?.ivShare -> {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_SUBJECT, "Baca berita menarik dari ${dataNews?.source?.name}")
                    putExtra(Intent.EXTRA_TEXT, dataNews?.title + "\nKlik link dibawah untuk selengkapnya ${dataNews?.url}")
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
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DATA_ITEM = "data"
    }

}