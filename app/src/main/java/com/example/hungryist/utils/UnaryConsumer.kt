package com.example.hungryist.utils

interface UnaryConsumer<T> {
    operator fun invoke(value: T)
}
