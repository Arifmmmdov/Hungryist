package com.example.hungryist.di

import android.content.Context
import android.content.SharedPreferences
import com.example.hungryist.ui.fragment.home.HomeViewModel
import com.example.hungryist.utils.SharedPreferencesManager
import com.example.hungryist.repo.Repository
import com.example.hungryist.ui.activity.detailedinfo.DetailedInfoViewModel
import com.example.hungryist.ui.activity.main.MainViewModel
import com.example.hungryist.ui.activity.splashscreen.SplashScreenViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

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
    fun getSplashScreenViewModel(sharedPreferencesManager: SharedPreferencesManager): SplashScreenViewModel {
        return SplashScreenViewModel(sharedPreferencesManager)
    }

    @Provides
    @Singleton
    fun mainViewModel(
        @ApplicationContext context: Context,
        repository: Repository,
    ): MainViewModel = MainViewModel(context, repository)

    @Provides
    @Singleton
    fun repository(): Repository = Repository()

    @Provides
    @Singleton
    fun homeViewModel(repository: Repository): HomeViewModel =
        HomeViewModel(repository)

    @Provides
    @Singleton
    fun getContext(@ApplicationContext context: Context): Context = context


    @Provides
    @Singleton
    fun getDetailedInfoViewModel(context: Context, repository: Repository): DetailedInfoViewModel =
        DetailedInfoViewModel(context, repository)


}
