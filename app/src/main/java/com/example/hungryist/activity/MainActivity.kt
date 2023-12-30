package com.example.hungryist.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hungryist.R
import com.example.hungryist.constants.Constants.IS_GUEST
import com.example.hungryist.databinding.ActivityMainBinding
import com.example.hungryist.databinding.CustomBottomNavigationItemBinding
import com.example.hungryist.fragment.login.LoginFragment


class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val bindingBottomNavItem by lazy {
        CustomBottomNavigationItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        changeBottomNavFocus()
        setBottomNavigationView()
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            var selectedFragment: Fragment? = null
            when (it.itemId) {
                R.id.action_home -> selectedFragment = LoginFragment()
                R.id.action_nearby_places -> selectedFragment = LoginFragment()
                R.id.action_saved -> selectedFragment = LoginFragment()
                R.id.action_profile -> selectedFragment = LoginFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.container, selectedFragment)
                    .commit()
            }
            changeBottomNavFocus()
            true
        }
    }

    private fun changeBottomNavFocus() {
        for (i in 0 until binding.bottomNavigationView.menu.size()) {
            val menuItem: MenuItem = binding.bottomNavigationView.menu.getItem(i)
            bindingBottomNavItem.icon.background = menuItem.icon
            bindingBottomNavItem.text.text = menuItem.title
            menuItem.actionView = bindingBottomNavItem.root
            println("Hello world $i")
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