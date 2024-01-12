package com.example.hungryist.model

import android.net.Uri

data class DetailedInfoModel(
    val freeWifi: Boolean,
    val bookingMandatory: Boolean,
    val liveMusicEveryNight: Boolean,
    val orderInAdvanced: Boolean,
    val smokingArea: Boolean,
    val studyingCondition: Boolean,
    val location: String,
    val phoneNumbers: List<String>,
    val websiteLink: String,
    val videoLocationUri: String,
    val imageUrl: String,
    val name: String,
    val reviews: String,
    val rating: Double,
    val reviewsList: List<ReviewsModel>,
    val menuList: List<MenuModel>,
)