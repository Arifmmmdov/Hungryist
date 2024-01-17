package com.example.hungryist.model

import java.util.Date

data class OpenCloseTimes(
    val day: String,
    val start: String,
    val end: String,
) {
    constructor() : this("", "", "")
}

data class RatingModel(
    val name: String,
    val rate: String,
) {
    constructor() : this("", "")
}