package com.katherineryn.moviezone.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.katherineryn.moviezone.R
import com.katherineryn.moviezone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? =  null
    private val binding get() = _activityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupMenu()
    }

    private fun setupMenu() {
        val navView: BottomNavigationView? = binding?.navView
        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        if (navView != null) {
            NavigationUI.setupWithNavController(
                navView,
                navController.navController
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}