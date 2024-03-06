package com.example.hungryist.model

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

class SearchMapPlaceModel(
    var latLng: LatLng?,
    val placeName: String,
) : Serializable {
    constructor(placeName: String) : this(null, placeName)
}