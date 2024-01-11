package com.example.hungryist.ui.activity.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hungryist.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    @Inject
    lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.startCountdownTimer(this)
    }

}