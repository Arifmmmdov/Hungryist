package com.example.hungryist.model

data class SelectStringModel(
    var id: String?,
    val place: String,
    var isSelected: Boolean = false,
) {
    constructor() : this("", "", false)
}

data class SelectPairString(
    var place:String,
    var typed:String
)