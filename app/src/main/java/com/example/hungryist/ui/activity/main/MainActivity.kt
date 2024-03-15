package com.example.hungryist.ui.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.hungryist.R
import com.example.hungryist.databinding.ActivityMainBinding
import com.example.hungryist.databinding.CustomBottomNavigationItemBinding
import com.example.hungryist.ui.fragment.home.HomeFragment
import com.example.hungryist.ui.fragment.login.LoginFragment
import com.example.hungryist.ui.fragment.profile.ProfileFragment
import com.example.hungryist.ui.fragment.saved.SavedFragment
import com.example.hungryist.utils.UserManager
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var userManager:UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        userManager.initializeSavedList()
        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        val navHost = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHost.navController

        NavigationUI.setupWithNavController(
            binding.bottomNavigationView, navController
        )
    }

    companion object {
        fun intentFor(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }
}