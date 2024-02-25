package com.example.hungryist.model

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class PlaceFilterModel(
    val isRestaurant: Boolean,
    val location: String,
    val distanceRange: IntRange,
    val priceRange: IntRange,
) : Serializable