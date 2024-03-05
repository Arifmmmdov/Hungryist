package com.example.hungryist.ui.fragment.profile

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    private val profileRepository: ProfileRepository,
) :
    ViewModel() {

    private val _profileInfo = MutableLiveData<ProfileInfoModel?>()
    val profileInfo = _profileInfo
    private val _selectedImageUrl = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String> = _selectedImageUrl

    init {
        if (profileRepository.uid == null)
            _profileInfo.value = null
        else
            profileRepository.getProfileInfo()
                .addOnSuccessListener {
                    _profileInfo.value = it
                }
                .addOnFailureListener {
                    Log.d("MyTagHere", "Failed call: ${it.message.toString()}")
                }
    }

    fun setSelectedImageUrl(imageUrl: String) {
        _selectedImageUrl.value = imageUrl
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

    fun openGallery(contentLauncher: ActivityResultLauncher<String>) {
        contentLauncher.launch("image/*")
    }

    fun uploadImage(imageUri: Uri) {
        profileRepository.uploadImage(imageUri)
            .addOnSuccessListener {
                setSelectedImageUrl(it.toString())
            }
            .addOnFailureListener {
                Log.e("MyTagHere", "uploadImage: ${it.message.toString()}")
            }
    }
}