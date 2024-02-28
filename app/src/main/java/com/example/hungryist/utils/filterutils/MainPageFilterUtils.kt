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
        }
    }

    fun filterRequest(): List<BaseInfoModel> {
        return if (isCustomFilterActive())
            filterCustom()
        else
            this.list
    }

    fun filterCustom(): List<BaseInfoModel> {
        return list.filter {
            (mealFilter == null || it.meals.any { it.cost.toInt() in mealFilter!!.priceRange }
                    && it.meals.any { it.name.contains(mealFilter!!.name) })
                    && (typed.isNullOrEmpty() || it.name.lowercase().contains(typed!!.lowercase()))
                    && (placeFilter == null || (((it.type == "Restaurants") == placeFilter?.isRestaurant)
                    || ((it.type == "Cafés") == placeFilter?.isCafe)
                    && it.meals.any { it.cost.toInt() in placeFilter!!.priceRange }))
        }
    }

    fun filterForPlaces(filterItems: PlaceFilterModel): List<BaseInfoModel> {
        category = null
        placeFilter = filterItems
        return filterCustom()
    }


    fun filterForMeals(filterItems: MealFilterModel): List<BaseInfoModel> {
        category = null
        mealFilter = filterItems
        return filterCustom()
    }

    fun filterable(): Boolean {
        return !(placeFilter == null
                && mealFilter == null
                && category == ""
                && typed == "")
    }

    fun isCustomFilterActive(): Boolean {
        return placeFilter != null || mealFilter != null
    }

    fun removeCustomFilter() {
        placeFilter = null
        mealFilter = null
    }

}