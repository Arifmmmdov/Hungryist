package com.example.hungryist.utils.filterutils

import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.MealFilterModel
import com.example.hungryist.model.PlaceFilterModel

class MainPageFilterUtils : BaseFilterUtils<BaseInfoModel>() {

    var placeFilter: PlaceFilterModel? = null
    var mealFilter: MealFilterModel? = null

    fun setBaseInfoList(list: List<BaseInfoModel>) {
        this.list = list
    }

    fun clearFilter() {
        category = ""
        typed = ""
        placeFilter = null
        mealFilter = null
    }

    override fun filter(): List<BaseInfoModel> {
        return list.filter {
            (category.isNullOrEmpty() || category == it.type)
                    && (typed.isNullOrEmpty() || it.name.lowercase().contains(typed!!.lowercase()))
                    && (placeFilter == null || checkPlaceFilter(it))
                    && (mealFilter == null || checkMealFilter(it) == true)
        }
    }

    private fun checkMealFilter(baseInfo: BaseInfoModel): Boolean? {
        return baseInfo.meals.any { it.cost.toInt() in mealFilter!!.priceRange }
                || baseInfo.meals.any { it.name.contains(mealFilter!!.name) }
    }

    private fun checkPlaceFilter(baseInfoModel: BaseInfoModel): Boolean {
        return ((baseInfoModel.type == "Restaurants") == placeFilter?.isRestaurant)
                || ((baseInfoModel.type == "Cafés") == placeFilter?.isCafe)
                || baseInfoModel.meals.any { it.cost.toInt() in placeFilter!!.priceRange }
    }

    fun filterForPlaces(filterItems: PlaceFilterModel): List<BaseInfoModel> {
        placeFilter = filterItems
        return filter()
    }


    fun filterForMeals(filterItems: MealFilterModel): List<BaseInfoModel> {
        mealFilter = filterItems
        return filter()
    }

    fun filterable(): Boolean {
        return !(placeFilter == null
                && mealFilter == null
                && category == ""
                && typed == "")
    }

    fun customFilterActive(): Boolean {
        return placeFilter != null || mealFilter != null
    }

    fun removeCustomFilter() {
        placeFilter = null
        mealFilter = null
    }

}