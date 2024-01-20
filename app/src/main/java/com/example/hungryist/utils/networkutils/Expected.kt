package com.example.hungryist.utils.networkutils

import com.example.hungryist.utils.UnaryConsumer

sealed class Expected<T> {
    class Success<T>(result: T) : Expected<T>()
    class Error<T>(message: String, code: Int) : Expected<T>()


    fun onError(message: UnaryConsumer<String>) {}
    fun onSuccess(result: UnaryConsumer<T>) {}
}