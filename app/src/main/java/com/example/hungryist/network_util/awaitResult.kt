package com.example.hungryist.network_util

import android.content.Context
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


public fun <Result, Response : BaseResponseAdapter<Result>> Call<Response>.awaitResult(
    context: Context,
    showProgressBar: Boolean
): Expected<Result>? {
    startProgressBar(context,showProgressBar)
    var expected: Expected<Result>? = null
    enqueue(object : Callback<Response> {
        override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
            val body = response.body() ?: return

            val status = body.status
            expected = if (status.equals(200) || status.equals(0)) {
                Expected.Success(body.getResult());
            } else
                Expected.Error(body.message, body.status.toInt())
        }

        override fun onFailure(call: Call<Response>, t: Throwable) {
            val gson = Gson()
            val error = gson.fromJson(t.message, ErrorResponse::class.java)
            expected = Expected.Error(error.message, error.status)
        }
    })
    return expected
}

fun startProgressBar(context: Context, showProgressBar: Boolean) {

}

data class ErrorResponse(
    val status: Int,
    val message: String
)
