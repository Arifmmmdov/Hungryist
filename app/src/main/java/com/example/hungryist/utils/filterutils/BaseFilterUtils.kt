package com.example.hungryist.utils.filterutils

abstract class BaseFilterUtils<T> {
    protected var category: String? = ""
    protected var typed: String? = ""

    protected var list: List<T> = listOf()


    fun filterForTypedText(text: String?): List<T> {
        typed = text
        return filter()
    }

    fun filterForCategory(category: String?): List<T> {
        this.category = category
        return filter()
    }

    abstract fun filter(): List<T>

}