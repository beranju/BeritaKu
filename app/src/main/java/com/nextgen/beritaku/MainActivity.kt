package com.nextgen.beritaku

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nextgen.beritaku.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ** delegation to disable dark mode
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // ** setup bottom navigation
        val navHostFragment =supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.home_navigation, R.id.explore_navigation, R.id.account_navigation
//        ).build()
        val navController =navHostFragment.navController
//        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{_, destination: NavDestination, _ ->
            when(destination.id){
                R.id.home_navigation -> visible()
                R.id.explore_navigation -> visible()
                R.id.account_navigation -> visible()
                else -> invisible()
            }
        }
    }

    private fun invisible() {
        binding.navView.visibility = View.GONE
        supportActionBar?.hide()
    }

    private fun visible() {
        binding.navView.visibility = View.VISIBLE
        supportActionBar?.show()
    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when(item.itemId){
//            R.id.favorite_menu -> {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("beritaku://favorite"))
//                startActivity(intent)
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_option_home, menu)
//        return super.onCreateOptionsMenu(menu)
//    }


}