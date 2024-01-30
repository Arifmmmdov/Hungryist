package com.example.hungryist.model

data class ProfileInfoModel(
    val name: String?,
    val surname: String?,
    val email: String?,
    val phoneNumber: String?,
    val imageUrl: String?,
    val rate: Double?,
    val reviews: Double?,
    val savedList: List<String>?,
) {
    constructor() : this("", "", "", "", "", 0.0, 0.0, listOf())

    constructor(registrationData: String?, isEmail: Boolean) : this(
        "",
        "",
        if (isEmail) registrationData else "",
        if (isEmail) "" else registrationData,
        "",
        0.0,
        0.0,
        listOf()
    )

}

