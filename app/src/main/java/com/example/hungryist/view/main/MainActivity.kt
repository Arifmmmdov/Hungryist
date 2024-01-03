package com.example.hungryist.view.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hungryist.R
import com.example.hungryist.constants.Constants.IS_GUEST
import com.example.hungryist.databinding.ActivityMainBinding
import com.example.hungryist.databinding.CustomBottomNavigationItemBinding
import com.example.hungryist.fragment.home.HomeFragment
import com.example.hungryist.fragment.login.LoginFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
        setBottomNavigationView()
        setObservers()
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
            R.id.action_profile -> LoginFragment()
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
            viewModel.setSelectedTab(it.groupId)
            true
        }
    }

    companion object {
        fun intentFor(activity: Activity, isGuest: Boolean) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra(IS_GUEST, isGuest)
            activity.startActivity(intent)
        }
    }
}