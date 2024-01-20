package com.example.hungryist.ui.activity.splashscreen

import android.app.Activity
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.hungryist.ui.activity.IntroActivity
import com.example.hungryist.ui.activity.main.MainActivity
import com.example.hungryist.utils.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {


    fun startCountdownTimer(activity: Activity) {

        object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                navigateToAnotherView(activity)
            }

        }.start()
    }

    private fun navigateToAnotherView(activity: Activity) {
        Log.d("MyTagHere", "navigateToAnotherView: $sharedPreferencesManager")
        if (checkUserRegistration())
            moveToMainActivity(activity)
        else
            moveToIntroActivity(activity)
    }


    private fun moveToIntroActivity(activity: Activity) {
        IntroActivity.intentFor(activity)
        activity.finish()
    }

    private fun moveToMainActivity(activity: Activity) {
        MainActivity.intentFor(activity, false)
        activity.finish()
    }

    private fun checkUserRegistration(): Boolean {
        return sharedPreferencesManager.isRegistered()
    }


}