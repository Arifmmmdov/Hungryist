package com.example.hungryist.model

data class SelectStringModel(
    var id: String?,
    val name: String,
    var isSelected: Boolean = false,
) {
    constructor() : this("", "", false)

    constructor(name:String) : this("", name,false)

    constructor(name:String,isSelected: Boolean) : this("", name,isSelected)
}