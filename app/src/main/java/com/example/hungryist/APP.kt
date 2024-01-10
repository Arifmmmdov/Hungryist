package com.example.hungryist

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import dagger.Component.Factory
import dagger.hilt.android.HiltAndroidApp
import java.util.prefs.Preferences
import javax.inject.Inject

@HiltAndroidApp
class APP : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}