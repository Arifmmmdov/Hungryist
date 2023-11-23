package com.example.hungryist.network

import com.example.hungryist.constants.BundleConstants.HOST
import com.example.hungryist.constants.BundleConstants.KEY
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

//code:"GBP"
//name:"British Pounds"
//symbol:"£"
data class Currency(
    val code: String,
    val name: String,
    val symbol: String,
)

class Currencies {
    private val client = OkHttpClient()

    private val request = Request.Builder()
        .url("https://restaurants222.p.rapidapi.com/currencies")
        .get()
        .addHeader(KEY, "c9c4e1b99dmsh446d9f65664ddc7p1b6c0ajsn7320c4dff434")
        .addHeader(HOST, "restaurants222.p.rapidapi.com")
        .build()

    val response: Response = client.newCall(request).execute()
}