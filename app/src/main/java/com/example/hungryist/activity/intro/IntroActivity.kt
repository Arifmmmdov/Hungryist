package com.example.hungryist.activity.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.hungryist.R
import com.example.hungryist.viewmodel.MViewModel
import com.example.hungryist.databinding.ActivityIntroBinding
import com.example.hungryist.fragment.SplashFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }

    private val viewModel:ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d("MaTagHer", "onCreate: ${viewModel.getValue(null,null).getString()}")
//        showFragment()
    }

    private fun showFragment() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id,SplashFragment())
            .commit()
    }
}