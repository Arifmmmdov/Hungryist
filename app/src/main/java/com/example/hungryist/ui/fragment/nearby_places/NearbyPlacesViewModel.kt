package com.example.hungryist.ui.fragment.nearby_places

import androidx.annotation.FloatRange
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.PlaceFilterModel
import com.example.hungryist.model.SearchMapPlaceModel
import com.example.hungryist.utils.filterutils.NearbyPlaceFilterModel
import com.example.hungryist.utils.filterutils.NearbyPlacesFilterUtils
import com.example.hungryist.utils.mapsearchplace.MapSearchPlaceUtils
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import javax.inject.Inject

class NearbyPlacesViewModel @Inject constructor() : ViewModel() {
    private val _filteredList = MutableLiveData(emptyList<BaseInfoModel>())
    val filteredList: LiveData<List<BaseInfoModel>> = _filteredList

    private val nearbyPlacesFilterUtils: NearbyPlacesFilterUtils by lazy {
        NearbyPlacesFilterUtils()
    }

    fun setCafeSelected(): Boolean {
        _filteredList.postValue(nearbyPlacesFilterUtils.triggerCafeSelection())
        return nearbyPlacesFilterUtils.nearbyPlaceFilterInfo.isCafeSelected
    }

    fun setRestaurantSelected(): Boolean {
        _filteredList.postValue(nearbyPlacesFilterUtils.triggerRestaurantSelection())
        return nearbyPlacesFilterUtils.nearbyPlaceFilterInfo.isRestaurantSelected
    }

    fun setFullList(fullList: MutableList<BaseInfoModel>) {
        nearbyPlacesFilterUtils.setFullList(fullList).also {
            _filteredList.postValue(it)
        }
    }

    fun setDistanceSlider(distanceRange: FloatRange) {
        _filteredList.postValue(nearbyPlacesFilterUtils.setDistanceRange(distanceRange))
    }

    fun setPlaceSelected(searchMapPlaceModel: SearchMapPlaceModel) {
        nearbyPlacesFilterUtils.setSelectedPLace(searchMapPlaceModel).also {
            _filteredList.postValue(it)
        }
    }

    fun getInfo(): NearbyPlaceFilterModel = nearbyPlacesFilterUtils.getFilterItems()
}