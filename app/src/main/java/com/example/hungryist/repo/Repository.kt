package com.example.hungryist.repo

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.DealsOfMonth
import com.example.hungryist.model.SelectStringModel
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
                        val baseInfoModel = document.toObject(BaseInfoModel::class.java)
                        baseInfoModel.id = document.id
                        resultList.add(baseInfoModel)
                    }
                    Tasks.forResult(resultList)
                } else {
                    Tasks.forException(task.exception!!)
                }
            }
    }

    fun getDealsOfMonths(): Task<MutableList<String>> {
        return db.collection("deals_of_month").get()
            .continueWithTask { task ->
                if (task.isSuccessful) {
                    val resultList = mutableListOf<String>()
                    for (document in task.result) {
                        val dealsOfMonths = document.toObject(DealsOfMonth::class.java)
                        resultList.add(dealsOfMonths.imageUrl)
                    }
                    Tasks.forResult(resultList)
                } else {
                    Tasks.forException(task.exception!!)
                }
            }
    }

    fun setDataSaved(id: String, saved: Boolean) {
        db.collection("baseInfoModel").document(id)
            .update("saved", saved)
    }

    fun getPlacesList(): Task<List<SelectStringModel>> {
        return db.collection("places").get()
            .continueWithTask {
                if (it.isSuccessful) {
                    val places = mutableListOf<SelectStringModel>()
                    for (document in it.result) {
                        val place = document.toObject(SelectStringModel::class.java)
                        places.add(place)
                    }
                    Tasks.forResult(places)
                } else
                    Tasks.forException(it.exception!!)
            }
    }
}