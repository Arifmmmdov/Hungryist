package com.example.hungryist.model

import com.google.android.gms.maps.model.LatLng

data class PlaceFilterModel (
    val isRestaurant:Boolean,
    val location:String, //TODO should be LatLng
    val distanceRange:IntRange,
    val priceRange:IntRange
)