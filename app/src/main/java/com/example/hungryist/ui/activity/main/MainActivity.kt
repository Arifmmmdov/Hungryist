package com.example.hungryist.ui.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hungryist.R
import com.example.hungryist.databinding.ActivityMainBinding
import com.example.hungryist.databinding.CustomBottomNavigationItemBinding
import com.example.hungryist.ui.fragment.home.HomeFragment
import com.example.hungryist.ui.fragment.login.LoginFragment
import com.example.hungryist.ui.fragment.profile.ProfileFragment
import com.example.hungryist.utils.Constant
import com.example.hungryist.utils.UserManager
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val bindingBottomNavItem by lazy {
        CustomBottomNavigationItemBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        UserManager.initializeSavedPlaces()
        FirebaseApp.initializeApp(this)
        setBottomNavigationView()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setSelectedTab(R.id.action_home)
    }

    private fun setObservers() {
        viewModel.selectedTabId.observe(this) {
            onTabSelected(it)
        }
    }

    private fun onTabSelected(tabId: Int?) {
        val selectedFragment: Fragment? = when (tabId) {
            R.id.action_home -> HomeFragment()
            R.id.action_nearby_places -> LoginFragment()
            R.id.action_saved -> LoginFragment()
            R.id.action_profile -> ProfileFragment()
            else -> null
        }

        selectedFragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, it)
                .commit()
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            viewModel.setSelectedTab(it.itemId)
            true
        }
    }

    companion object {
        fun intentFor(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }
}