package com.example.hungryist.model

data class MealModel(
    var id: String,
    val name: String,
    val cost: Double,
) {

    constructor() : this("", "", 0.0)
}