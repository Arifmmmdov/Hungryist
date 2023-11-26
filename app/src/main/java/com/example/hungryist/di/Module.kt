package com.example.hungryist.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.hungryist.helper.SharedPreferencesManager
import com.example.hungryist.api.APIRequestInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun apiService(): APIRequestInterface {
        return Retrofit.Builder()
            .baseUrl("https://restaurants222.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIRequestInterface::class.java)
    }

    @Provides
    @Singleton
    fun getSharedPreference(@ApplicationContext context:Context):SharedPreferences {
        return context.getSharedPreferences("",Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun getSharedPreferencesManager(sharedPreferences: SharedPreferences):SharedPreferencesManager{
        return SharedPreferencesManager(sharedPreferences)
    }
}