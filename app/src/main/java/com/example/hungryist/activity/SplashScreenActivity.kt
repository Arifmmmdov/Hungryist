package com.example.hungryist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.example.hungryist.APP
import com.example.hungryist.R
import com.example.hungryist.databinding.ActivitySplashScreenBinding
import com.example.hungryist.helper.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val REGISTERED = "registered"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                navigateToAnotherView()
            }

        }.start()
    }

    private fun navigateToAnotherView() {
        Log.d("MyTagHere", "navigateToAnotherView: $sharedPreferencesManager")
        if (checkUserRegistration())
            moveToMainActivity()
        else
            moveToIntroActivity()
    }

    private fun moveToIntroActivity() {
        IntroActivity.intentFor(this)
        finish()
    }

    private fun moveToMainActivity() {
        MainActivity.intentFor(this, false)
        finish()
    }

    private fun checkUserRegistration(): Boolean {
        return sharedPreferencesManager.getBoolean(REGISTERED, false)
    }
}