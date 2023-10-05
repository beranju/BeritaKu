package com.nextgen.beritaku.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nextgen.beritaku.core.databinding.ForYouItemBinding
import com.nextgen.beritaku.core.domain.model.NewsDataItem
import com.nextgen.beritaku.core.utils.DateUtils.dateFormat
import com.nextgen.beritaku.core.utils.DateUtils.dateToTimeAgo
import com.nextgen.beritaku.core.utils.ExtentionFun.loadImage
import com.nextgen.beritaku.core.utils.Utils.calculateReadTime

class ForYouAdapter : ListAdapter<NewsDataItem, ForYouAdapter.ForYouViewHolder>(DIFF_UTIL) {

    var onClick: ((NewsDataItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForYouViewHolder {
        return ForYouViewHolder(
            ForYouItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ForYouViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ForYouViewHolder(val binding: ForYouItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsDataItem?) {
            with(binding) {
                tvTitle.text = item?.title.orEmpty()
                tvCategory.text = item?.category?.first()
                tvAuthor.text =
                    if (item?.creator.equals("null")) item?.sourceId else item?.creator
                tvReadTime.text = calculateReadTime(item?.content.orEmpty())
                tvDate.text = dateToTimeAgo(item?.pubDate.orEmpty())
                sivPhoto.loadImage(item?.imageUrl.toString())
            }
        }

        init {
            binding.root.setOnClickListener {
                onClick?.invoke(getItem(adapterPosition))
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<NewsDataItem>() {
            override fun areItemsTheSame(oldItem: NewsDataItem, newItem: NewsDataItem): Boolean =
                oldItem.articleId == newItem.articleId

            override fun areContentsTheSame(oldItem: NewsDataItem, newItem: NewsDataItem): Boolean =
                oldItem == newItem
        }
    }
}