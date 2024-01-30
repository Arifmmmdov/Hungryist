package com.example.hungryist.utils

import com.example.hungryist.model.ProfileInfoModel
import com.example.hungryist.repo.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object UserManager {

    lateinit var savedList: MutableList<String>

    fun initializeSavedPlaces() {
        ProfileRepository().initializePlaces()
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

        val profileRepository = ProfileRepository()
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
        val profileRepository = ProfileRepository()
        profileRepository.createProfile(ProfileInfoModel(registrationData, isEmail))

    }

    fun checkSaved(id: String): Boolean {
        return savedList.contains(id)
    }
}