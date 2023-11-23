package com.example.hungryist.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getRetrofitBuilder(): APIService {
    return Retrofit.Builder()
        .baseUrl("https://restaurants222.p.rapidapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIService::class.java)
}