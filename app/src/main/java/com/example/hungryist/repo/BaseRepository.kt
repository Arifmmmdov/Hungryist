package com.example.hungryist.repo

import android.util.Log
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.DealsOfMonth
import com.example.hungryist.model.OpenCloseStatusModel
import com.example.hungryist.model.SelectStringModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class BaseRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getBaseInfoList(): Task<MutableList<BaseInfoModel>> {
        return db.collection("detailedInfoModel").get().continueWithTask { task ->
            if (task.isSuccessful) {
                val resultList = mutableListOf<BaseInfoModel>()
                for (document in task.result) {
                    val baseInfoModel = document.toObject(BaseInfoModel::class.java)
                    baseInfoModel.id = document.id

//                    runBlocking {
//                        val openCloseList = getOpenCloseDate(document.id).await()
//                        baseInfoModel.openCloseTimes = openCloseList
//                    }

                    resultList.add(baseInfoModel)
                }
                Tasks.forResult(resultList)
            } else {
                Tasks.forException(task.exception!!)
            }
        }
    }

    fun getOpenCloseDate(id: String): Task<List<OpenCloseStatusModel>> {
        return db.collection("detailedInfoModel").document(id).collection("openCloseTimes").get()
            .continueWithTask {
                if (it.isSuccessful) {
                    val openCloseTimes = mutableListOf<OpenCloseStatusModel>()
                    for (document in it.result) {
                        val openCloseTime = document.toObject(OpenCloseStatusModel::class.java)
                        openCloseTimes.add(openCloseTime)
                    }
                    Tasks.forResult(openCloseTimes)
                } else {
                    Tasks.forException(it.exception!!)
                }
            }
    }

    fun getDealsOfMonths(): Task<MutableList<String>> {
        return db.collection("deals_of_month").get().continueWithTask { task ->
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

    fun getPlacesList(): Task<List<SelectStringModel>> {
        return db.collection("places").get().continueWithTask {
            if (it.isSuccessful) {
                val places = mutableListOf<SelectStringModel>()
                for (document in it.result) {
                    val place = document.toObject(SelectStringModel::class.java)
                    places.add(place)
                }
                Tasks.forResult(places)
            } else Tasks.forException(it.exception!!)
        }
    }

}