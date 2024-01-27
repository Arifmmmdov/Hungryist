package com.example.hungryist.ui.fragment.profile

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.hungryist.ui.activity.intro.IntroActivity
import com.example.hungryist.ui.activity.main.MainActivity
import com.example.hungryist.utils.SharedPreferencesManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val context: Context, val sharedPreferencesManager: SharedPreferencesManager):
    ViewModel() {

    fun logOut(activity: Activity) {
        FirebaseAuth.getInstance().signOut()
        sharedPreferencesManager.setRegistered(false)
        IntroActivity.intentFor(activity)
    }
}