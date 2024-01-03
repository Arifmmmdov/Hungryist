package com.example.hungryist.model

data class MenuModel(
    val name: String,
    val cost: Double,
    val description: String,
    val onSale: Boolean,
    val rate: Double,
    val notifyMe: Boolean,
    val imageUrl:String
)