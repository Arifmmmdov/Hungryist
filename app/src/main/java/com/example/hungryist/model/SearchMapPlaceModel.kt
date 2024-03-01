package com.example.hungryist.model

import com.google.android.gms.maps.model.LatLng

class SearchMapPlaceModel(
    var latLng: LatLng?,
    val placeName: String,
) {
    constructor(placeName: String) : this(null, placeName)
}