package com.example.hungryist.utils

import android.content.SharedPreferences
import com.example.hungryist.utils.Constant.REGISTERED
import javax.inject.Inject


class SharedPreferencesManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun getString(key: String, defaultValue: String) {
        sharedPreferences.getString(key, defaultValue)
    }

    fun setString(key: String, item: String) {
        sharedPreferences.edit().putString(key, item).apply()
    }

    fun isRegistered(): Boolean {
        return sharedPreferences.getBoolean(REGISTERED, false)
    }

    fun setRegistered() {
        sharedPreferences.edit().putBoolean(REGISTERED, true).apply()
    }
}