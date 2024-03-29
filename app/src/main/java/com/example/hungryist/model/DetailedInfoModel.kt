package com.example.hungryist.model

import com.google.firebase.firestore.GeoPoint

data class DetailedInfoModel(
    val id: String,
    val referenceId: String,
    val baseImage: String,
    val titleName: String,
    val type: String,
    val geoPoint: GeoPoint,
    val freeWifi: Boolean,
    val bookingMandatory: Boolean,
    val liveMusicEveryNight: Boolean,
    val orderInAdvanced: Boolean,
    val smokingArea: Boolean,
    val studyingCondition: Boolean,
    val location: String,
    var phoneNumbers: List<String>,
    val websiteLink: String,
    val videoLocationUri: String?,
    val imageUrl: String,
    val name: String,
    val reviews: String,
    val overallRating: Double,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        GeoPoint(0.0, 0.0),
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
    )
}