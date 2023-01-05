package com.nextgen.beritaku.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nextgen.beritaku.R
import com.nextgen.beritaku.core.domain.model.NewsModel
import com.nextgen.beritaku.core.utils.DateUtils
import com.nextgen.beritaku.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "detail"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val data = arguments?.getParcelable<NewsModel>(DATA_ITEM)
        Log.d(TAG, "$data")
        if (data != null){
            setupView(data)
        }

    }

    private fun setupView(data: NewsModel?) {
        data.let {
            binding.apply {
                title.text = data?.title
                label.text = data?.source?.name ?: "Anonim"
                description.text = data?.description
                author.text = data?.author ?: "Anonim"
                date.text = DateUtils.dateFormat(data?.publishedAt.toString())
                content.text = data?.content ?: ""
                Glide.with(requireContext())
                    .load(data?.urlToImage)
                    .apply(RequestOptions().placeholder(R.drawable.ic_load_data).error(R.drawable.ic_empty_data))
                    .centerCrop()
                    .into(thumbnail)
                readmore.setOnClickListener{
                    val bundle = Bundle()
                    bundle.putString(WebFragment.URL, data?.url)
                    findNavController().navigate(R.id.action_detailFragment_to_webFragment, bundle)
                }
                var isFavorite = data!!.isFavorite
                setStatusFavorite(isFavorite)
                favorite.setOnClickListener {
                    isFavorite = !isFavorite
                    viewModel.setFavoriteNews(data, isFavorite)
                    setStatusFavorite(isFavorite)
                }
            }
        }

    }

    private fun setStatusFavorite(favorite: Boolean) {
        if (favorite){
            binding.favorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_24))
        }else{
            binding.favorite.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_border_24))
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
        const val TAG = "DetailFragment"
        const val DATA_ITEM = "data"
    }
}