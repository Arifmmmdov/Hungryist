package com.example.hungryist.activity.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.hungryist.R
import com.example.hungryist.databinding.ActivityIntroBinding
import com.example.hungryist.fragment.SplashFragment
import com.example.hungryist.network.Currencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IntroActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showFragment()
    }

    private fun showFragment() {
        lifecycleScope.launch(Dispatchers.IO) {
            Log.d("MyTagHere", "showFragment: ${Currencies().response.request.body.toString()}")
        }
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id,SplashFragment())
            .commit()
    }
}