package com.example.hungryist.di

import android.content.Context
import android.content.SharedPreferences
import com.example.hungryist.repo.BaseRepository
import com.example.hungryist.repo.DetailedInfoRepository
import com.example.hungryist.utils.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseModule {

    @Provides
    @Singleton
    fun getSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("", Context.MODE_PRIVATE)
    }

    @Provides
    fun getSharedPreferencesManager(sharedPreferences: SharedPreferences): SharedPreferencesManager {
        return SharedPreferencesManager(sharedPreferences)
    }


    @Provides
    @Singleton
    fun baseRepository(): BaseRepository = BaseRepository()

    @Provides
    @Singleton
    fun detailedInfoRepository(): DetailedInfoRepository = DetailedInfoRepository()


    @Provides
    @Singleton
    fun getContext(@ApplicationContext context: Context): Context = context


}
