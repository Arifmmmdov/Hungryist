package com.example.hungryist.model

import android.net.Uri

data class DetailedInfoModel(
    val id:String,
    val referenceId:String,
    var saved:Boolean,
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
    var reviewsList: List<ReviewsModel>,
    var menuList: List<MenuModel>,
) {
    constructor() : this(
        "",
        "",
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        "",
        emptyList(),
        "",
        "",
        "",
        "",
        "",
        0.0,
        emptyList(),
        emptyList()
    )
}