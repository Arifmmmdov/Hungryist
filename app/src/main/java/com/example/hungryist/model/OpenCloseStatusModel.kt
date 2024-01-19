package com.example.hungryist.model

data class OpenCloseStatusModel(
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