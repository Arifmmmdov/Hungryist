package com.example.hungryist.model

import android.media.Rating
import com.google.firebase.firestore.GeoPoint

data class BaseInfoModel(
    var id: String,
    val baseImage: String,
    val titleName: String,
    val type: String,
    val location: String,
    val name: String,
    val reviews: String,
    val overallRating: Double,
    var openCloseTimes:List<OpenCloseStatusModel>
) {
    constructor() : this("","", "", "", "", "", "",0.0, listOf())
}