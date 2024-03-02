package com.example.hungryist.model

import androidx.annotation.FloatRange
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import java.io.Serializable

data class PlaceFilterModel(
    val isRestaurant: Boolean,
    val isCafe: Boolean,
    val location: SearchMapPlaceModel?,
    val distanceRange: FloatRange,
    val priceRange: IntRange,
) : Serializable