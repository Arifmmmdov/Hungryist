package com.example.hungryist.utils

import com.example.hungryist.model.ProfileInfoModel
import com.example.hungryist.repo.ProfileRepository
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserManager @Inject constructor(private val profileRepository: ProfileRepository) {

    lateinit var savedList: MutableList<String>


    fun initializeSavedList(){
        if (profileRepository.sharedPreferencesManager.getUserId() == null)
            savedList = mutableListOf()
        else
            profileRepository.initializePlaces()
                .addOnSuccessListener {
                    println("Document link created successfully$it")
                    savedList = it.toMutableList()
                }
                .addOnFailureListener {
                    println("MyTagHere:Error creating document link: ${it.message.toString()}")
                    this.savedList = mutableListOf()
                }
    }

    fun triggerSavedPlace(placeLink: String) {

        val isExist = savedList.contains(placeLink)

        if (isExist) {
            profileRepository.deleteSaved(placeLink)
            savedList.removeIf {
                it == placeLink
            }
        } else {
            profileRepository.addSaved(placeLink)
            savedList.add(placeLink)
        }

    }

    fun createProfile(registrationData: String?, isEmail: Boolean) {
        profileRepository.createProfile(ProfileInfoModel(registrationData, isEmail))
    }

    fun checkSaved(id: String): Boolean {
        return savedList.contains(id)
    }

    fun getUserId() = profileRepository.sharedPreferencesManager.getUserId()
}