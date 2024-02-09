package com.example.hungryist.model

import com.google.android.gms.maps.model.LatLng

data class PlaceFilterModel (
    val isRestaurant:Boolean,
    val location:LatLng,
    val distanceRange:IntRange,
    val priceRange:IntRange
)