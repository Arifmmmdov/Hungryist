package com.example.hungryist.model

import android.provider.ContactsContract.CommonDataKinds.Email

data class SimpleDataModel (
    val name:String,
    val surname:String,
    val age:Int,
    val email: Email
)