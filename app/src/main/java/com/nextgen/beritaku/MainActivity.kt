package com.nextgen.beritaku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nextgen.beritaku.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // ** setup bottom navigation
        val navHostFragment =supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController =navHostFragment.navController
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{_, destination: NavDestination, _ ->
            when(destination.id){
                R.id.home_navigation -> visible()
                R.id.explore_navigation -> visible()
                R.id.favorite_navigation -> visible()
                R.id.account_navigation -> visible()
                else -> invisible()
            }
        }

    }

    private fun invisible() {
        binding.navView.visibility = View.GONE
    }

    private fun visible() {
        binding.navView.visibility = View.VISIBLE
    }


}