package com.nextgen.beritaku.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.beritaku.core.ui.NewsAdapter
import com.nextgen.beritaku.detail.DetailFragment
import com.nextgen.beritaku.favorite.databinding.ActivityFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private val viewModel: FavoriteViewModel by viewModels()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter()
    }
    private val binding: ActivityFavoriteBinding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        newsAdapter.viewType = 2
        viewModel.favoriteNews.observe(this){result->
            newsAdapter.setData(result)
            newsAdapter.onClick = {selectedItem->
                val intent = Intent(this, DetailFragment::class.java)
                intent.putExtra(DetailFragment.DATA_ITEM, selectedItem)
                startActivity(intent)
            }
        }
        binding.rvFavoriteNews.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = newsAdapter
        }


    }
}