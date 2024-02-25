package com.example.hungryist.di

import android.content.Context
import com.example.hungryist.repo.BaseRepository
import com.example.hungryist.repo.DetailedInfoRepository
import com.example.hungryist.repo.ProfileRepository
import com.example.hungryist.ui.activity.detailedinfo.DetailedInfoViewModel
import com.example.hungryist.ui.activity.filter.FilterViewModel
import com.example.hungryist.ui.activity.main.MainViewModel
import com.example.hungryist.ui.activity.splashscreen.SplashScreenViewModel
import com.example.hungryist.ui.fragment.home.HomeViewModel
import com.example.hungryist.ui.fragment.menu.MenuViewModel
import com.example.hungryist.ui.activity.intro.IntroViewModel
import com.example.hungryist.ui.fragment.profile.ProfileViewModel
import com.example.hungryist.ui.fragment.reviews.ReviewsViewModel
import com.example.hungryist.utils.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    @Singleton
    fun getSplashScreenViewModel(
        context: Context,
        sharedPreferencesManager: SharedPreferencesManager,
    ): SplashScreenViewModel {
        return SplashScreenViewModel(context, sharedPreferencesManager)
    }

    @Provides
    @Singleton
    fun getProfileViewModel(
        @ApplicationContext context: Context,
        sharedPreferencesManager: SharedPreferencesManager,
        profileRepository: ProfileRepository,
    ): ProfileViewModel {
        return ProfileViewModel(context, sharedPreferencesManager, profileRepository)
    }

    @Provides
    @Singleton
    fun mainViewModel(
        @ApplicationContext context: Context,
        repository: BaseRepository,
    ): MainViewModel = MainViewModel(context, repository)

    @Provides
    @Singleton
    fun homeViewModel(
        @ApplicationContext context: Context,
        repository: BaseRepository,
    ): HomeViewModel =
        HomeViewModel(context, repository)

    @Provides
    @Singleton
    fun getDetailedInfoViewModel(
        context: Context,
        repository: DetailedInfoRepository,
    ): DetailedInfoViewModel =
        DetailedInfoViewModel(context, repository)


    @Provides
    @Singleton
    fun getMenuViewModel(context: Context, repository: DetailedInfoRepository): MenuViewModel =
        MenuViewModel(context, repository)


    @Provides
    @Singleton
    fun getReviewsViewModel(
        context: Context,
        repository: DetailedInfoRepository,
    ): ReviewsViewModel =
        ReviewsViewModel(context, repository)

    @Provides
    @Singleton
    fun getFilterViewModel(): FilterViewModel =
        FilterViewModel()


    @Provides
    @Singleton
    fun getRegisterViewModel(
        context: Context,
        sharedPreferencesManager: SharedPreferencesManager,
    ): IntroViewModel =
        IntroViewModel(context, sharedPreferencesManager)


}