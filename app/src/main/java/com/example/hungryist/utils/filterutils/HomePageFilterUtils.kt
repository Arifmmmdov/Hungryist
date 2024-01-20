package com.example.hungryist.utils.filterutils

import com.example.hungryist.model.BaseInfoModel

object HomePageFilterUtils : BaseFilterUtils<BaseInfoModel>() {

    fun setBaseInfoList(list: List<BaseInfoModel>) {
        this.list = list
        category = ""
        typed = ""
    }

    override fun filter(): List<BaseInfoModel> {
        return list.filter {
            (category.isNullOrEmpty() || category == it.type)
                    && (typed.isNullOrEmpty() || it.name.lowercase().contains(typed!!.lowercase()))
        }
    }
}