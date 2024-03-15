package com.example.hungryist.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.hungryist.repo.BaseRepository
import com.example.hungryist.repo.DetailedInfoRepository
import com.example.hungryist.repo.ProfileRepository
import com.example.hungryist.utils.SharedPreferencesManager
import com.example.hungryist.utils.UserManager
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseModule {

    @Provides
    @Singleton
    @Named("shared_preferences")
    fun getSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @Named("encrypted_shared_preferences")
    fun getEncryptedSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "encrypted_shared_preferences",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    fun getSharedPreferencesManager(
        @Named("shared_preferences") sharedPreferences: SharedPreferences,
        @Named("encrypted_shared_preferences") encryptedSharedPreferences: SharedPreferences,
    ): SharedPreferencesManager {
        return SharedPreferencesManager(sharedPreferences, encryptedSharedPreferences)
    }


    @Provides
    @Singleton
    fun baseRepository(): BaseRepository = BaseRepository()

    @Provides
    @Singleton
    fun getProfileRepository(sharedPreferencesManager: SharedPreferencesManager): ProfileRepository =
        ProfileRepository(sharedPreferencesManager)

    @Provides
    @Singleton
    fun detailedInfoRepository(): DetailedInfoRepository = DetailedInfoRepository()


    @Provides
    @Singleton
    fun getContext(@ApplicationContext context: Context): Context = context


    @Provides
    @Singleton
    fun getFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


    @Provides
    @Singleton
    fun getUserManager(profileRepository: ProfileRepository): UserManager =
        UserManager(profileRepository)


}
