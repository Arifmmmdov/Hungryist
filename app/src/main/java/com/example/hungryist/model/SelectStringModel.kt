package com.example.hungryist.model

data class SelectStringModel(
    var id: String?,
    val place: String,
    var isSelected: Boolean = false,
) {
    constructor() : this("", "", false)
}