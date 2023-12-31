package com.example.hungryist.model

import android.media.Rating

data class BaseInfoModel(
    val name: String,
    val location: String,
    val openStatus: String,
    val openTime: String,
    val closeTime: String,
    val rating: Double,
    val reviews: String,
    val saved: Boolean,
    val imageUrl: String,
    val titleName: String? = null
)