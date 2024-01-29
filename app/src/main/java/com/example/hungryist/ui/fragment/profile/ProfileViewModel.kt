package com.example.hungryist.ui.fragment.profile

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.ProfileInfoModel
import com.example.hungryist.repo.ProfileRepository
import com.example.hungryist.ui.activity.EditProfileActivity
import com.example.hungryist.ui.activity.intro.IntroActivity
import com.example.hungryist.ui.activity.main.MainActivity
import com.example.hungryist.utils.SharedPreferencesManager
import com.example.hungryist.utils.extension.showToastMessage
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val context: Context,
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val profileRepository: ProfileRepository,
) :
    ViewModel() {

    private val _profileInfo = MutableLiveData<ProfileInfoModel>()
    val profileInfo = _profileInfo

    init {
        profileRepository.getProfileInfo()
            .addOnSuccessListener {
                _profileInfo.value = it
            }
            .addOnFailureListener {
                context.showToastMessage(it.message.toString())
            }

    }

    fun logOut(activity: Activity) {
        FirebaseAuth.getInstance().signOut()
        sharedPreferencesManager.setRegistered(false)
        IntroActivity.intentFor(activity)
    }

    fun saveChanges(context: Context, profileInfo: ProfileInfoModel) {
        _profileInfo.value = profileInfo
        profileRepository.saveProfileChanges(context, profileInfo)
        MainActivity.intentFor(context)
    }


}