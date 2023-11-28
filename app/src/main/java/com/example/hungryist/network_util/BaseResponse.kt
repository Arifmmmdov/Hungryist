package com.example.hungryist.network_util

import com.google.gson.annotations.SerializedName

abstract class BaseResponse<T> {
    @SerializedName("status")
    lateinit var status: Integer

    @SerializedName("msg")
    lateinit var message: String

    abstract fun getResult(): T
}