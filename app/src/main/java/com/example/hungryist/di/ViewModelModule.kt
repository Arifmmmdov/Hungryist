package com.example.hungryist.di

import android.content.Context
import com.example.hungryist.repo.BaseRepository
import com.example.hungryist.repo.DetailedInfoRepository
import com.example.hungryist.repo.ProfileRepository
import com.example.hungryist.ui.activity.detailedinfo.DetailedInfoViewModel
import com.example.hungryist.ui.activity.splashscreen.SplashScreenViewModel
import com.example.hungryist.ui.fragment.home.HomeViewModel
import com.example.hungryist.ui.fragment.menu.MenuViewModel
import com.example.hungryist.ui.activity.intro.IntroViewModel
import com.example.hungryist.ui.activity.searchlocation.SearchLocationViewModel
import com.example.hungryist.ui.fragment.nearby_places.NearbyPlacesViewModel
import com.example.hungryist.ui.fragment.profile.ProfileViewModel
import com.example.hungryist.ui.fragment.reviews.ReviewsViewModel
import com.example.hungryist.utils.SharedPreferencesManager
import com.example.hungryist.utils.UserManager
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
        sharedPreferencesManager: SharedPreferencesManager,
        baseRepository: BaseRepository,
    ): SplashScreenViewModel {
        return SplashScreenViewModel(sharedPreferencesManager, baseRepository)
    }

    @Provides
    @Singleton
    fun getProfileViewModel(
        sharedPreferencesManager: SharedPreferencesManager,
        profileRepository: ProfileRepository,
    ): ProfileViewModel {
        return ProfileViewModel(sharedPreferencesManager, profileRepository)
    }

    @Provides
    @Singleton
    fun homeViewModel(
        repository: BaseRepository,
        sharedPreferencesManager: SharedPreferencesManager,
        userManager: UserManager,
    ): HomeViewModel =
        HomeViewModel(repository, sharedPreferencesManager, userManager)

    @Provides
    @Singleton
    fun getDetailedInfoViewModel(
        sharedPreferencesManager: SharedPreferencesManager,
        repository: DetailedInfoRepository,
        userManager: UserManager,
    ): DetailedInfoViewModel =
        DetailedInfoViewModel(sharedPreferencesManager, repository, userManager)


    @Provides
    @Singleton
    fun getMenuViewModel(repository: DetailedInfoRepository): MenuViewModel =
        MenuViewModel(repository)

    @Provides
    @Singleton
    fun getNearbyPlacesViewModel(): NearbyPlacesViewModel =
        NearbyPlacesViewModel()


    @Provides
    @Singleton
    fun getReviewsViewModel(
        context: Context,
        repository: DetailedInfoRepository,
    ): ReviewsViewModel =
        ReviewsViewModel(context, repository)


    @Provides
    @Singleton
    fun getRegisterViewModel(
        userManager: UserManager,
        sharedPreferencesManager: SharedPreferencesManager,
    ): IntroViewModel =
        IntroViewModel(userManager, sharedPreferencesManager)


    @Provides
    @Singleton
    fun getSearchLocationViewModel(): SearchLocationViewModel = SearchLocationViewModel()

}