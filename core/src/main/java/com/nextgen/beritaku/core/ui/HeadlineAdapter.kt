package com.nextgen.beritaku.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nextgen.beritaku.core.databinding.ItemHeadlineNewsBinding
import com.nextgen.beritaku.core.domain.model.NewsModel

class HeadlineAdapter: RecyclerView.Adapter<HeadlineAdapter.NewsViewHolder>(){

    private val listNews = ArrayList<NewsModel>()
    var onItemClick: ((NewsModel) -> Unit)? = null

    fun setData(listNewsData: List<NewsModel>?){
        if (listNewsData == null) return
        listNews.clear()
        listNews.addAll(listNewsData)
        notifyDataSetChanged()
    }


    inner class NewsViewHolder(private val binding: ItemHeadlineNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NewsModel) {
            binding.apply {
                titleHeadline.text = data.title
                timeHeadline.text = data.publishedAt
                author.text = data.author
                Glide.with(itemView.context)
                    .load(data.urlToImage)
                    .centerCrop()
                    .into(thumbnailHeadline)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listNews[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemHeadlineNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val data = listNews[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listNews.size
}