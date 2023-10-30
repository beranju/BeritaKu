package com.nextgen.beritaku.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.beritaku.core.ui.ForYouAdapter
import com.nextgen.beritaku.favorite.databinding.ActivityFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteActivity : AppCompatActivity() {
    private val viewModel: FavoriteViewModel by viewModel()
    private val newsAdapter: ForYouAdapter by lazy { ForYouAdapter() }
    private val binding: ActivityFavoriteBinding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadKoinModules(favoriteModule)

        supportActionBar?.title = getString(R.string.favorite_activity_title)

        binding.rvFavoriteNews.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
        viewModel.error.observe(this) { error ->
            if (error != null) {
                Log.e("FavoriteActivity", error.toString())
            }
        }
        viewModel.news.observe(this) {
            newsAdapter.submitList(it)
            Log.d("FavoriteActivity", "data : $it")
        }
        viewModel.loading.observe(this) {
        }
        binding.emptyFavorite.btnFindNews.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("beritaku://explore"))
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}