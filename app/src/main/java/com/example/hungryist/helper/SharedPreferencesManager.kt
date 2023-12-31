package com.example.hungryist.helper

import android.content.SharedPreferences
import javax.inject.Inject


class SharedPreferencesManager(private val sharedPreferences: SharedPreferences) {

    fun getString(key: String, defaultValue: String) {
        sharedPreferences.getString(key, defaultValue)
    }

    fun setString(key: String, item: String) {
        sharedPreferences.edit().putString(key, item).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
}