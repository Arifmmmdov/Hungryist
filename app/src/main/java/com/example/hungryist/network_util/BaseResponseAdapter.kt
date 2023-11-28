package com.example.hungryist.network_util

import com.google.gson.annotations.SerializedName

class BaseResponseAdapter<Result>(@SerializedName("results") val value: Result) :
    BaseResponse<Result>() {
    override fun getResult(): Result {
        return value
    }

}
