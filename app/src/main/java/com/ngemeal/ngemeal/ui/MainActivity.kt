package com.ngemeal.ngemeal.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ngemeal.ngemeal.Ngemeal
import com.ngemeal.ngemeal.R
import com.ngemeal.ngemeal.databinding.ActivityMainBinding
import com.ngemeal.ngemeal.ui.auth.AuthActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Ngemeal.getApp().getUser().toString().isNullOrEmpty() || Ngemeal.getApp().getToken().toString().isNullOrEmpty()) {
            val auth = Intent(this, AuthActivity::class.java)
            startActivity(auth)
            finish()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_order, R.id.navigation_profile
//            )
//        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        val navController = Navigation.findNavController(this,R.id.nav_host_fragment_activity_main)
        NavigationUI.setupWithNavController(navView, navController)
        //navView.setupWithNavController(navController)
    }

}