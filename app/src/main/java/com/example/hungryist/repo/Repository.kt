package com.example.hungryist.repo

import android.content.Context
import android.widget.Toast
import com.example.hungryist.model.BaseInfoModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.qualifiers.ApplicationContext

class Repository {


    private val db = FirebaseFirestore.getInstance()

    fun getBaseInfoList(): Task<MutableList<BaseInfoModel>> {
        return db.collection("baseInfoModel").get()
            .continueWithTask { task ->
                if (task.isSuccessful) {
                    val resultList = mutableListOf<BaseInfoModel>()
                    for (document in task.result) {
                        resultList.add(document.toObject(BaseInfoModel::class.java))
                    }
                    Tasks.forResult(resultList)
                } else {
                    Tasks.forException(task.exception!!)
                }
            }
    }

}