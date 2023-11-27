package com.example.hungryist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hungryist.APP
import com.example.hungryist.R
import com.example.hungryist.databinding.ActivitySplashScreenBinding
import com.example.hungryist.helper.SharedPreferencesManager
import com.example.hungryist.viewmodel.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class SplashScreenActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startCountdownTimer(this)
    }


}