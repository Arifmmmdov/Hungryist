package com.example.hungryist.network_util

import com.example.hungryist.helper.UnaryConsumer

sealed class Expected<T> {
    class Success<T>(result: T) : Expected<T>()
    class Error<T>(message: String, code: Int) : Expected<T>()


    fun onError(message: UnaryConsumer<String>) {}
    fun onSuccess(result: UnaryConsumer<T>) {}
}