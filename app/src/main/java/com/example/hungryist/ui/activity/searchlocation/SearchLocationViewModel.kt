package com.example.hungryist.ui.activity.searchlocation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.model.SearchMapPlaceModel
import com.example.hungryist.utils.extension.showToastMessage
import com.example.hungryist.utils.mapsearchplace.MapSearchPlaceUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.AutocompletePrediction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchLocationViewModel @Inject constructor() : ViewModel() {
    lateinit var mMap: GoogleMap
    private var _selectedLocation: MutableLiveData<SearchMapPlaceModel?> =
        MutableLiveData<SearchMapPlaceModel?>()
    var selectedLocation: LiveData<SearchMapPlaceModel?> = _selectedLocation


    fun setCurrentLocationClicked(
        searchPlaceUtils: MapSearchPlaceUtils,
        currentLocation: LatLng?,
        context: Context,
    ) {
        if (currentLocation != null) {
            _selectedLocation.value =
                searchPlaceUtils.getPlaceNameFromLatLng(context, currentLocation)
        } else
            context.showToastMessage("Your current location is not available!")

    }

    fun setPlaceSelected(
        location: SearchMapPlaceModel,
    ) {
        _selectedLocation.postValue(location)
    }

    fun setPlaceSelected(
        prediction: AutocompletePrediction?,
        searchPlaceUtils: MapSearchPlaceUtils,
    ) {
        if (prediction == null)
            _selectedLocation.value = null
        else
            searchPlaceUtils.getLatLngPlace(prediction.placeId) {
                _selectedLocation.value =
                    SearchMapPlaceModel(it, prediction.getPrimaryText(null).toString())
            }
    }

    fun placeSelectedActionMap(
        prediction: AutocompletePrediction,
        searchPlaceUtils: MapSearchPlaceUtils,
    ) {
        _selectedLocation.postValue(SearchMapPlaceModel(prediction.getPrimaryText(null).toString()))
        searchPlaceUtils.getLatLngPlace(prediction.placeId) {
            mMap.clear()
            addMarker(it)
            _selectedLocation.value?.latLng = it
        }
    }

    fun addMarker(location: LatLng, iconResource: Int? = null) {
        mMap.addMarker(
            MarkerOptions().position(location).apply {
                if (iconResource != null)
                    icon(BitmapDescriptorFactory.fromResource(iconResource))
            }
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10F))
    }

    fun validatePlaceSelection(text: String, searchPlaceUtils: MapSearchPlaceUtils) {
        if (selectedLocation.value == null)
            searchPlaceUtils.searchPlaces(text) {
                setPlaceSelected(it[0], searchPlaceUtils)
            }
    }


}