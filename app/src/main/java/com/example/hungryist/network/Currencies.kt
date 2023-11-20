package com.example.hungryist.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class Currencies {
    private val client = OkHttpClient()

    private val request = Request.Builder()
        .url("https://restaurants222.p.rapidapi.com/currencies")
        .get()
        .addHeader("X-RapidAPI-Key", "c9c4e1b99dmsh446d9f65664ddc7p1b6c0ajsn7320c4dff434")
        .addHeader("X-RapidAPI-Host", "restaurants222.p.rapidapi.com")
        .build()

    val response: Response = client.newCall(request).execute()
}