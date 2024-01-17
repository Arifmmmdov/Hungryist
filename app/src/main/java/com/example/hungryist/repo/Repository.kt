package com.example.hungryist.repo

import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.DealsOfMonth
import com.example.hungryist.model.DetailedInfoModel
import com.example.hungryist.model.MenuModel
import com.example.hungryist.model.OpenCloseTimes
import com.example.hungryist.model.RatingModel
import com.example.hungryist.model.ReviewsModel
import com.example.hungryist.model.SelectStringModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore

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

    fun setDataSaved(baseInfoId: String, detailedInfoId: String, saved: Boolean) {
        db.collection("baseInfoModel").document(baseInfoId)
            .update("saved", saved)

        db.collection("detailedInfoModel").document(detailedInfoId)
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

    fun getDetailedInfo(id: String): Task<DetailedInfoModel> {
        return db.collection("detailedInfoModel").document(id).get()
            .continueWithTask { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.exists()) {
                        val phones = document.get("phoneNumbers") as List<String>
                        val detailedInfo = document.toObject(DetailedInfoModel::class.java)
                        detailedInfo?.phoneNumbers = phones
                        Tasks.forResult(detailedInfo)
                    } else {
                        Tasks.forException(task.exception!!)
                    }
                } else {
                    Tasks.forException(task.exception!!)
                }
            }

    }

    fun getMenuList(id: String): Task<List<MenuModel>> {
        return db.collection("detailedInfoModel").document(id).collection("menuModel").get()
            .continueWithTask {
                if (it.isSuccessful) {
                    val menuList = mutableListOf<MenuModel>()
                    for (document in it.result) {
                        val menu = document.toObject(MenuModel::class.java)
                        menuList.add(menu)
                    }
                    Tasks.forResult(menuList)
                } else {
                    Tasks.forException(it.exception!!)
                }
            }
    }

    fun getReviewsList(id: String): Task<List<ReviewsModel>> {
        return db.collection("detailedInfoModel").document(id).collection("reviewsModel").get()
            .continueWithTask {
                if (it.isSuccessful) {
                    val reviews = mutableListOf<ReviewsModel>()
                    for (document in it.result) {
                        val reviewModel = document.toObject(ReviewsModel::class.java)
                        reviews.add(reviewModel)
                    }
                    Tasks.forResult(reviews)
                } else {
                    Tasks.forException(it.exception!!)
                }
            }
    }

    fun getOpenCloseDate(id: String): Task<List<OpenCloseTimes>> {
        return db.collection("detailedInfoModel").document(id).collection("openCloseTimes").get()
            .continueWithTask {
                if (it.isSuccessful) {
                    val openCloseTimes = mutableListOf<OpenCloseTimes>()
                    for (document in it.result) {
                        val openCloseTime = document.toObject(OpenCloseTimes::class.java)
                        openCloseTimes.add(openCloseTime)
                    }
                    Tasks.forResult(openCloseTimes)
                } else {
                    Tasks.forException(it.exception!!)
                }
            }
    }

    fun getRatingList(id: String): Task<List<RatingModel>> {
        return db.collection("detailedInfoModel").document(id).collection("raings").get()
            .continueWithTask {
                if (it.isSuccessful) {
                    val ratingList = mutableListOf<RatingModel>()
                    for (document in it.result) {
                        val rating = document.toObject(RatingModel::class.java)
                        ratingList.add(rating)
                    }
                    Tasks.forResult(ratingList)
                } else {
                    Tasks.forException(it.exception!!)
                }
            }
    }
}