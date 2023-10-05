package com.nextgen.beritaku.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.beritaku.detail.DetailFragment
import com.nextgen.beritaku.favorite.databinding.ActivityFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteActivity : AppCompatActivity() {
    private val viewModel: FavoriteViewModel by viewModel()
    private val binding: ActivityFavoriteBinding by lazy {
        ActivityFavoriteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite_activity_title)

        loadKoinModules(favoriteModule)
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