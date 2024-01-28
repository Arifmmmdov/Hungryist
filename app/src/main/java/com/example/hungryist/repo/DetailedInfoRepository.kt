package com.example.hungryist.repo

import com.example.hungryist.model.DealsOfMonth
import com.example.hungryist.model.DetailedInfoModel
import com.example.hungryist.model.MenuModel
import com.example.hungryist.model.OpenCloseStatusModel
import com.example.hungryist.model.RatingModel
import com.example.hungryist.model.ReviewsModel
import com.example.hungryist.model.SelectStringModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore

class DetailedInfoRepository {


    private val db = FirebaseFirestore.getInstance()
    private lateinit var id: String


    fun setDataSaved(baseInfoId: String, detailedInfoId: String, saved: Boolean) {
        db.collection("baseInfoModel").document(baseInfoId)
            .update("saved", saved)

        db.collection("detailedInfoModel").document(detailedInfoId)
            .update("saved", saved)
    }

    fun getDetailedInfo(): Task<DetailedInfoModel> {
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

    fun getMenuList(): Task<List<MenuModel>> {
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

    fun getInteriorList(): Task<List<String>> {
        return db.collection("detailedInfoModel").document(id).collection("interior").get()
            .continueWithTask {
                if (it.isSuccessful) {
                    val menuList = mutableListOf<String>()
                    for (document in it.result) {
                        val menu = document.toObject(DealsOfMonth::class.java)
                        menuList.add(menu.imageUrl)
                    }
                    Tasks.forResult(menuList)
                } else {
                    Tasks.forException(it.exception!!)
                }
            }
    }

    fun getMenuCategoriesList(): Task<List<SelectStringModel>> {
        return db.collection("detailedInfoModel").document(id).collection("categories").get()
            .continueWithTask {
                if (it.isSuccessful) {
                    val menuList = mutableListOf<SelectStringModel>()
                    for (document in it.result) {
                        val menu = document.toObject(SelectStringModel::class.java)
                        menuList.add(menu)
                    }
                    Tasks.forResult(menuList)
                } else {
                    Tasks.forException(it.exception!!)
                }
            }
    }

    fun getReviewsList(): Task<List<ReviewsModel>> {
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

    fun getOpenCloseDate(): Task<List<OpenCloseStatusModel>> {
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

    fun getRatingList(): Task<List<RatingModel>> {
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

    fun setPlaceId(id: String) {
        this.id = id
    }
}