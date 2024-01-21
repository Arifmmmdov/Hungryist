package com.example.hungryist.model

import android.provider.Telephony.Mms.Rate

data class ReviewsModel(
    val profile: String,
    val customer: String,
    val images: List<String>,
    val message: String,
    val restaurantName: String,
    val rate: Double,
    val wroteOn: String,
    val reply: Int,
    val like: Int,
    val dislike: Int,
) {
    constructor() : this("", "", listOf(), "", "", 0.0, "", 0, 0, 0)
}