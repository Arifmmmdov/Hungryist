package com.example.hungryist.utils.mapsearchplace

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.hungryist.R
import com.example.hungryist.model.SearchMapPlaceModel
import com.example.hungryist.utils.UnaryConsumer
import com.example.hungryist.utils.extension.showToastMessage
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.PlacesStatusCodes
import java.util.Arrays
import java.util.Locale

class MapSearchPlaceUtils(val context: Context) {

    private lateinit var placesClient: PlacesClient
    private lateinit var sessionToken: AutocompleteSessionToken

    init {
        initializePlace()
    }

    fun getLatLngPlace(placeId: String, callback: (LatLng) -> Unit) {
        val placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        placesClient.fetchPlace(request)
            .addOnSuccessListener {
                val latLng = it.place.latLng
                if (latLng != null) {
                    callback(latLng)
                }

            }
            .addOnFailureListener {
                context.showToastMessage(it.message.toString())
            }
    }

    private fun initializePlace() {
        if (!Places.isInitialized()) {
            Places.initialize(context, context.getString(R.string.api_key))
        }

        placesClient = Places.createClient(context)
        sessionToken = AutocompleteSessionToken.newInstance()
    }

    fun searchPlaces(searchedText: String, callback: (List<AutocompletePrediction>) -> Unit) {
        val bias = RectangularBounds.newInstance(
            LatLng(22.458744, 88.208162),
            LatLng(22.730671, 88.35434)
        )

        val newRequest = FindAutocompletePredictionsRequest
            .builder()
            .setSessionToken(sessionToken)
            .setTypeFilter(TypeFilter.ESTABLISHMENT)
            .setQuery(searchedText)
            .setLocationBias(bias)
            .build()

        placesClient.findAutocompletePredictions(newRequest)
            .addOnSuccessListener {
                val predictions: List<AutocompletePrediction> = it.autocompletePredictions
                callback(predictions)
            }.addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }

    }

    fun getPlaceNameFromLatLng(
        context: Context,
        latLng: LatLng,
    ): SearchMapPlaceModel? {

        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        return addresses?.get(0)?.let { SearchMapPlaceModel(latLng, it.getAddressLine(0)) }
//        val bias = RectangularBounds.newInstance(
//            LatLng(22.458744, 88.208162),
//            LatLng(22.730671, 88.35434)
//        )
//
//        val request = FindAutocompletePredictionsRequest.builder()
//            .setLocationBias(bias)
//            .setOrigin(latLng)
//            .setSessionToken(sessionToken)
//            .setQuery("")
//            .build()
//
//        placesClient.findAutocompletePredictions(request)
//            .addOnSuccessListener { response ->
//                if (response.autocompletePredictions.isNotEmpty()) {
//                    callback(response.autocompletePredictions[0])
//                }
//            }
//            .addOnFailureListener { exception ->
//                context.showToastMessage(exception.message.toString())
//
//            }
    }

}