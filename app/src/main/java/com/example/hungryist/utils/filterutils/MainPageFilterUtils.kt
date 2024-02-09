package com.example.hungryist.utils.filterutils

import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.PlaceFilterModel

class MainPageFilterUtils : BaseFilterUtils<BaseInfoModel>() {

    private var placeFilter: PlaceFilterModel? = null

    fun setBaseInfoList(list: List<BaseInfoModel>) {
        this.list = list
        resetData()
    }

    fun resetData() {
        category = ""
        typed = ""
    }

    override fun filter(): List<BaseInfoModel> {
        return list.filter {
            (category.isNullOrEmpty() || category == it.type)
                    && (typed.isNullOrEmpty() || it.name.lowercase().contains(typed!!.lowercase()))
                    && (placeFilter == null || checkPlaceFilter(it))
        }
    }

    private fun checkPlaceFilter(baseInfoModel: BaseInfoModel): Boolean {
        return ((baseInfoModel.type == "Restaurant") == placeFilter?.isRestaurant)
                || ()
    }

    fun filterForPlaces(filterItems: PlaceFilterModel) {
        placeFilter = filterItems
    }
}