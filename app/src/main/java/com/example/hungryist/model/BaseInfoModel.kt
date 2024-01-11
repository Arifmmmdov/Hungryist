package com.example.hungryist.model

import android.media.Rating

data class BaseInfoModel(
    var id: String,
    val name: String,
    val location: String,
    val openStatus: String,
    val openTime: String,
    val closeTime: String,
    val rating: Double,
    val reviews: String,
    val type: String,
    var saved: Boolean,
    val imageUrl: String,
    val titleName: String? = null,
) {
    constructor() : this("", "", "", "", "", "", 0.0, "", "", false, "", null)
}