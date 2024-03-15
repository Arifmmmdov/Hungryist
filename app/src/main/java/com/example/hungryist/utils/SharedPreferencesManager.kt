package com.example.hungryist.utils

import android.content.SharedPreferences
import com.example.hungryist.utils.Constant.REGISTERED
import com.example.hungryist.utils.Constant.USER_ID
import javax.inject.Inject


class SharedPreferencesManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val encryptedSharedPreferences: SharedPreferences,
) {

    fun getString(key: String, defaultValue: String) {
        sharedPreferences.getString(key, defaultValue)
    }

    fun isRegistered(): Boolean {
        return sharedPreferences.getBoolean(REGISTERED, false)
    }

    fun setRegistered(isRegistered: Boolean) {
        sharedPreferences.edit().putBoolean(REGISTERED, isRegistered).apply()
    }

    fun setUserId(userId: String?) {
        encryptedSharedPreferences.edit().putString(USER_ID, userId).apply()
    }

    fun getUserId(): String? {
        return encryptedSharedPreferences.getString(USER_ID, null)
    }
}