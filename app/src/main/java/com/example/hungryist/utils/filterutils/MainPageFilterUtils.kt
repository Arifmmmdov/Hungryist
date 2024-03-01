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
            checkTypedText(it)
                    && checkMeal(it)
                    && checkPlace(it)
        }
    }

    private fun checkPlace(baseInfo: BaseInfoModel): Boolean =
        placeFilter == null
                || (((baseInfo.type == "Restaurants") == placeFilter?.isRestaurant) || ((baseInfo.type == "Cafés") == placeFilter?.isCafe))
                && baseInfo.meals.any { it.cost.toInt() in placeFilter!!.priceRange }
                && (placeFilter?.location == null || DistanceCalculator().checkDistance(
            placeFilter?.location?.latLng, baseInfo.geoPoint, placeFilter?.distanceRange
        ))

    private fun checkTypedText(baseInfo: BaseInfoModel): Boolean =
        typed.isNullOrEmpty() || baseInfo.name.lowercase().contains(typed!!.lowercase())

    private fun checkMeal(baseInfo: BaseInfoModel): Boolean =
        mealFilter == null || baseInfo.meals.any { it.cost.toInt() in mealFilter!!.priceRange }
                && baseInfo.meals.any { it.name.contains(mealFilter!!.name) }

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