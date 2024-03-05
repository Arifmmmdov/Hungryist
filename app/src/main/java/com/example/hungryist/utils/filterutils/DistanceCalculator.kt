package com.example.hungryist.utils.filterutils

import androidx.annotation.FloatRange
import com.google.firebase.firestore.GeoPoint
import com.google.android.gms.maps.model.LatLng
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class DistanceCalculator {
    private val EARTH_RADIUS = 6371

    fun checkDistance(from: LatLng?, to: GeoPoint?, givenDistance: FloatRange?): Boolean {

        val distance = to?.let { from?.let { it1 -> calculateDistance(it1, it) } }

        return if (distance != null) {
            distance >= givenDistance?.from!! && distance <= givenDistance?.to!!
        } else  true
    }

    fun calculateDistance(from: LatLng, to: GeoPoint): Double {
        val dLat = Math.toRadians(to.latitude - from.latitude)
        val dLon = Math.toRadians(to.longitude - from.longitude)
        val lat1 = Math.toRadians(from.latitude)
        val lat2 = Math.toRadians(to.latitude)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                sin(dLon / 2) * sin(dLon / 2) * cos(lat1) * cos(lat2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return EARTH_RADIUS * c
    }
}