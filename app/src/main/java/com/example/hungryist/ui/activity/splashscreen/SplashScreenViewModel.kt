package com.example.hungryist.ui.activity.splashscreen

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.hungryist.ui.activity.intro.IntroActivity
import com.example.hungryist.ui.activity.main.MainActivity
import com.example.hungryist.utils.SharedPreferencesManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    val context: Context,
    private val sharedPreferencesManager: SharedPreferencesManager,
) : ViewModel() {

    private lateinit var auth: FirebaseAuth

    private fun intentTo(isRegistered: Boolean) {
        if (isRegistered)
            MainActivity.intentFor(context)
        else
            IntroActivity.intentFor(context)
    }

    fun checkRegistered(callback: () -> Unit) {
        intentTo(sharedPreferencesManager.isRegistered())
        callback()
    }
}