package com.example.hungryist.ui.activity.detailedinfo

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hungryist.R
import com.example.hungryist.model.DetailedInfoModel
import com.example.hungryist.model.OpenCloseStatusModel
import com.example.hungryist.model.RatingModel
import com.example.hungryist.repo.DetailedInfoRepository
import com.example.hungryist.ui.activity.MapsActivity
import com.example.hungryist.utils.RestaurantStatusChecker
import com.example.hungryist.utils.SharedPreferencesManager
import com.example.hungryist.utils.UserManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class DetailedInfoViewModel @Inject constructor(
    val sharedPreferencesManager: SharedPreferencesManager,
    val repository: DetailedInfoRepository,
    val userManager: UserManager,
) :
    ViewModel() {
    private val _detailedInfo = MutableLiveData<DetailedInfoModel>()
    val detailedInfo: LiveData<DetailedInfoModel> = _detailedInfo
    private val _ratingList = MutableLiveData<List<RatingModel>>()
    val ratingList: LiveData<List<RatingModel>> = _ratingList
    private val _openClosedDateList = MutableLiveData<List<OpenCloseStatusModel>>()
    val openClosedDateList: LiveData<List<OpenCloseStatusModel>> = _openClosedDateList
    private val _interiorList = MutableLiveData<List<String>>()
    val interiorList: LiveData<List<String>> = _interiorList

    fun getDetailedInfo() {
        repository.getDetailedInfo()
            .addOnSuccessListener {
                _detailedInfo.value = it
                getOtherDataAsync()
            }
            .addOnFailureListener {
                Log.e("MyTagHere", "getDetailedInfo: ${it.message}")
            }
    }

    private fun getOtherDataAsync() {


        repository.getOpenCloseDate()
            .addOnSuccessListener {
                Log.d("TestResult", "getOtherDataAsync: $it")
                _openClosedDateList.value = it
            }

        repository.getRatingList()
            .addOnSuccessListener {
                Log.d("TestResult", "getOtherDataAsync: $it")
                _ratingList.value = it
            }

        getInteriorList()
    }

    private fun getInteriorList() {
        repository.getInteriorList()
            .addOnSuccessListener {
                _interiorList.value = it
            }
    }

    fun setUpMapParameters(googleMap: GoogleMap, context: Context) {
        val location = com.google.android.gms.maps.model.LatLng(
            detailedInfo.value?.geoPoint!!.latitude,
            detailedInfo.value?.geoPoint!!.longitude
        )

        val customMarkerIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_marker)
        val customMarker = BitmapDescriptorFactory.fromBitmap(customMarkerIcon)

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        val marker = googleMap.addMarker(
            MarkerOptions().position(location).title(detailedInfo.value?.name).icon(customMarker)
        )
        marker?.showInfoWindow()

        googleMap.uiSettings.isScrollGesturesEnabled = false
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f))
    }

    fun isCurrentlyOpen(): Boolean {
        return RestaurantStatusChecker.isRestaurantOpen(openClosedDateList.value)
    }

    fun setPlaceId(id: String?) {
        repository.setPlaceId(id!!)
    }

    fun checkUserRegistration(): Boolean {
        return sharedPreferencesManager.isRegistered()
    }

    fun triggerSavedPlace(id: String) {
        userManager.triggerSavedPlace(id)
    }

    fun checkSaved(id: String): Boolean = userManager.checkSaved(id)


}