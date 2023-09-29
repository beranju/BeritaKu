package com.nextgen.beritaku.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nextgen.beritaku.core.databinding.HeadlineItemBinding
import com.nextgen.beritaku.core.domain.model.NewsDataItem

class HeadlineAdapter : ListAdapter<NewsDataItem, HeadlineAdapter.HeadlineViewHolder>(DIFF_UTIL) {
    var onClick: ((NewsDataItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        return HeadlineViewHolder(
            HeadlineItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class HeadlineViewHolder(val binding: HeadlineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsDataItem?) {
            with(binding) {
                tvCategory.text = item?.category?.first()
                tvSource.text = item?.sourceId
                tvTitleNews.text = item?.title.orEmpty()
                Glide.with(itemView.context)
                    .load(item?.imageUrl)
                    .into(ivThumbnail)
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