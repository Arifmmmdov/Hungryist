package com.example.hungryist.model

import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.hungryist.utils.extension.showToastMessage
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

data class ProfileInfoModel(
    val name: String?,
    val surname: String?,
    val email: String?,
    val phoneNumber: String?,
    val imageUrl: String?,
    val rate: Double?,
    val reviews: Double?,
    val savedList: List<String>?,
) {
    constructor() : this("", "", "", "", "", 0.0, 0.0, listOf())

    constructor(registrationData: String?, isEmail: Boolean) : this(
        "",
        "",
        if (isEmail) registrationData else "",
        if (isEmail) "" else registrationData,
        "",
        0.0,
        0.0,
        listOf()
    )

}

object GlobalProfileInfo {

    private val db = FirebaseFirestore.getInstance()
    private val firestore = FirebaseFirestore.getInstance()


    fun createProfile(uid: String?, registrationData: String?, isEmail: Boolean) {
        val profileInfoModel = ProfileInfoModel(registrationData, isEmail)
        firestore.collection("profileInfo").document(uid!!).set(profileInfoModel)
            .addOnSuccessListener {
                println("Document link created successfully$it")
            }
            .addOnFailureListener { e ->
                println("Error creating document link: ${e.message.toString()}")
            }

    }
}