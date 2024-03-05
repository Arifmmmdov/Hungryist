package com.example.hungryist.repo

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.hungryist.model.ProfileInfoModel
import com.example.hungryist.utils.extension.showToastMessage
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import java.util.UUID

class ProfileRepository {
    private val db by lazy {
        FirebaseFirestore.getInstance()
    }
    val uid by lazy {
        FirebaseAuth.getInstance().uid
    }
    private val storage by lazy {
        FirebaseStorage.getInstance()
    }

    fun saveProfileChanges(context: Context, profileInfo: ProfileInfoModel?) {
        if (profileInfo != null) {
            db.collection("profileInfo").document(uid ?: "Ic7QHNcYwix9rX5pkehn").set(profileInfo)
                .addOnCompleteListener {
                    Log.d("MyTagHere", "Successfully completed!")
                }
                .addOnFailureListener {
                    context.showToastMessage(it.message.toString())
                }
        }
    }

    fun getProfileInfo(): Task<ProfileInfoModel?> {
        return db.collection("profileInfo").document(uid!!).get()
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

    fun addSaved(placeLink: String) {
        db.collection("profileInfo").document(uid!!).collection("saved")
            .document()
            .set(hashMapOf("saved" to placeLink))
            .addOnSuccessListener {
                Log.d("MyTagHere", "addSaved: ")
            }.addOnFailureListener {
                Log.d("MyTagHere", "addSaved: ${it.message.toString()}")
            }

    }

    fun deleteSaved(placeLink: String) {
        db.collection("profileInfo").document(uid!!).collection("saved")
            .whereEqualTo("saved", placeLink)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    document.reference.delete()
                        .addOnSuccessListener {
                            Log.d("MyTagHere", "deleteSaved: ")
                        }
                        .addOnFailureListener {
                            Log.d("MyTagHere", "deleteSaved: ${it.message.toString()}")
                        }
                }
            }
    }

    fun createProfile(profileInfoModel: ProfileInfoModel) {
        db.collection("profileInfo").document(uid!!).set(profileInfoModel)
            .addOnSuccessListener {
                println("Document link created successfully$it")
            }
            .addOnFailureListener { e ->
                println("Error creating document link: ${e.message.toString()}")
            }
    }

    fun initializePlaces(): Task<List<String>> {
        return db.collection("profileInfo").document(uid!!).collection("saved")
            .get()
            .continueWithTask {
                if (it.isSuccessful) {
                    val savedList = mutableListOf<String>()
                    for (document in it.result) {
                        val saved = document.getString("saved")
                        saved?.let {
                            savedList.add(it)
                        }
                    }
                    Tasks.forResult(savedList)

                } else {
                    Tasks.forException(it.exception!!)
                }

            }
    }

    fun uploadImage(imageUri: Uri): Task<String> {
        val imageRef = storage.reference.child("profileImages/${UUID.randomUUID()}.jpg")

        return imageRef.putFile(imageUri)
            .continueWithTask { task ->
                if (task.isSuccessful) {
                    imageRef.downloadUrl
                } else {
                    Tasks.forException(task.exception!!)
                }
            }
            .continueWith { downloadUrlTask ->
                if (downloadUrlTask.isSuccessful) {
                    downloadUrlTask.result.toString()
                } else {
                    throw downloadUrlTask.exception!!
                }
            }
    }

}