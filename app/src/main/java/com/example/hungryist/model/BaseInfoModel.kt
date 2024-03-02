package com.example.hungryist.model

import android.media.Rating
import com.google.firebase.firestore.GeoPoint

data class BaseInfoModel(
    var id: String,
    val baseImage: String,
    val titleName: String,
    val geoPoint: GeoPoint?,
    val type: String,
    val location: String,
    val name: String,
    val reviews: String,
    val overallRating: Double,
    var openCloseTimes: List<OpenCloseStatusModel>,
    var meals: List<MealModel>,
) {
    constructor() : this("", "", "",null, "", "", "", "", 0.0, listOf(), listOf())
}