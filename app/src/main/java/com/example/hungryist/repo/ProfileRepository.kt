package com.example.hungryist.repo

import android.content.Context
import com.example.hungryist.model.ProfileInfoModel
import com.example.hungryist.utils.extension.showToastMessage
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileRepository {
    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().uid

    fun saveProfileChanges(context: Context, profileInfo: ProfileInfoModel?) {
        if (profileInfo != null) {
            db.collection("profileInfo").document(uid ?: "Ic7QHNcYwix9rX5pkehn").set(profileInfo)
                .addOnCompleteListener {
                    context.showToastMessage("Successfully completed!")
                }
                .addOnFailureListener {
                    context.showToastMessage(it.message.toString())
                }
        }
    }

    fun getProfileInfo(): Task<ProfileInfoModel?> {
        return db.collection("profileInfo").document(uid ?: "Ic7QHNcYwix9rX5pkehn").get()
            .continueWithTask {
                if (it.isSuccessful) {

                    val document = it.result
                    if (document != null && document.exists()) {

                        val profileInfo = document.toObject(ProfileInfoModel::class.java)
                        Tasks.forResult(profileInfo)

                    } else
                        Tasks.forException(it.exception!!)
                } else
                    Tasks.forException(it.exception!!)


            }
    }
}