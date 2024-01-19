package com.example.hungryist.utils

import com.example.hungryist.model.BaseInfoModel

object FilterUtils {
    private var baseList: List<BaseInfoModel> = listOf()

    private var category: String? = ""
    private var typed: String? = ""

    fun setList(list: List<BaseInfoModel>) {
        this.baseList = list
        category = ""
        typed = ""
    }

    fun filterForTypedText(text: String?): List<BaseInfoModel> {
        typed = text
        return filter()
    }

    fun filterForCategory(category: String): List<BaseInfoModel> {
        this.category = category
        return filter()
    }

    private fun filter(): List<BaseInfoModel> {
        return baseList.filter {
            (category.isNullOrEmpty() || category == it.type) && (typed.isNullOrEmpty() || it.name.lowercase()
                .contains(typed!!.lowercase()))
        }
    }
}