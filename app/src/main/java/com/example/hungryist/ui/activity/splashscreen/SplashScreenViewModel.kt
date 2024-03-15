package com.example.hungryist.ui.activity.splashscreen

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.hungryist.BuildConfig
import com.example.hungryist.repo.BaseRepository
import com.example.hungryist.ui.activity.intro.IntroActivity
import com.example.hungryist.ui.activity.main.MainActivity
import com.example.hungryist.utils.SharedPreferencesManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val repository: BaseRepository,
) : ViewModel() {


    fun checkRegistered(callback: (Boolean) -> Unit) {
        callback(sharedPreferencesManager.isRegistered())
    }

    fun checkApplicationVersion(callback: (Boolean) -> Unit) {
        repository.getVersionCode().addOnSuccessListener {
            callback(it.toInt() <= BuildConfig.VERSION_CODE)
        }
            .addOnFailureListener {
                callback(false)
            }
    }
}