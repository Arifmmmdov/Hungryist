package com.example.hungryist.repo

import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.DealsOfMonth
import com.example.hungryist.model.MealModel
import com.example.hungryist.model.MenuModel
import com.example.hungryist.model.OpenCloseStatusModel
import com.example.hungryist.model.SelectStringModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore

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

    fun getMenuCost(id: String): Task<List<MealModel>> {
        return db.collection("detailedInfoModel").document(id).collection("menuModel").get()
            .continueWithTask {
                if (it.isSuccessful) {
                    val meals = mutableListOf<MealModel>()
                    for (document in it.result) {
                        val mealModel = document.toObject(MealModel::class.java)
                        mealModel.id = id
                        meals.add(mealModel)
                    }
                    Tasks.forResult(meals)
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

    fun getVersionCode():Task<String> {
        return   db.collection("general_infos").get().continueWithTask {
            if (it.isSuccessful) {
                var versionCode:String? = null
                for (document in it.result) {
                    versionCode = document.getString("version_code")
                }
                Tasks.forResult(versionCode)
            } else Tasks.forException(it.exception!!)
        }
    }

}