package com.example.hungryist.model

data class SelectStringModel(
    var id: String?,
    val name: String,
    var isSelected: Boolean = false,
) {
    constructor() : this("", "", false)

    constructor(name:String) : this("", name,false)
}

data class SelectPairString(
    var place:String,
    var typed:String
)