package com.example.hungryist.utils.filterutils

import androidx.annotation.FloatRange
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.SearchMapPlaceModel

data class NearbyPlaceFilterModel(
    var selectedPlace: SearchMapPlaceModel? = null,
    var isRestaurantSelected: Boolean = true,
    var isCafeSelected: Boolean = true,
    var distanceRange: FloatRange? = null,
)

class NearbyPlacesFilterUtils {

    var fullList: List<BaseInfoModel> = emptyList()

    var nearbyPlaceFilterInfo = NearbyPlaceFilterModel()

    fun setFullList(fullList: List<BaseInfoModel>): List<BaseInfoModel> {
        this.fullList = fullList
        checkDistanceRange()
        return filter()
    }

    private fun checkDistanceRange() {
        if (nearbyPlaceFilterInfo.distanceRange == null)
            nearbyPlaceFilterInfo.distanceRange = FloatRange(0.0, 1500.0)
    }

    fun triggerCafeSelection(): List<BaseInfoModel> {
        nearbyPlaceFilterInfo.isCafeSelected = !nearbyPlaceFilterInfo.isCafeSelected
        return filter()
    }

    fun triggerRestaurantSelection(): List<BaseInfoModel> {
        nearbyPlaceFilterInfo.isRestaurantSelected = !nearbyPlaceFilterInfo.isRestaurantSelected
        return filter()
    }

    fun setDistanceRange(distanceRange: FloatRange): List<BaseInfoModel> {
        this.nearbyPlaceFilterInfo.distanceRange = distanceRange
        return filter()
    }

    private fun filter(): List<BaseInfoModel> {
        return fullList.filter {
            checkItem(it)
        }
    }

    fun getFilterItems() = nearbyPlaceFilterInfo

    private fun checkItem(item: BaseInfoModel): Boolean {
        return (nearbyPlaceFilterInfo.selectedPlace == null || DistanceCalculator().checkDistance(
            nearbyPlaceFilterInfo.selectedPlace?.latLng,
            item.geoPoint,
            nearbyPlaceFilterInfo.distanceRange ?: FloatRange(0.0, 1500.0)
        ))
                && ((nearbyPlaceFilterInfo.isRestaurantSelected && item.type == "Restaurants") || (nearbyPlaceFilterInfo.isCafeSelected && (item.type == "Cafés")))
    }

    fun setSelectedPLace(selectedPlace: SearchMapPlaceModel): List<BaseInfoModel> {
        this.nearbyPlaceFilterInfo.selectedPlace = selectedPlace
        return filter()
    }
}