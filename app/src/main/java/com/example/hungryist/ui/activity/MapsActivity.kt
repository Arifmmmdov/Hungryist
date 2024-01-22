package com.example.hungryist.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hungryist.R
import com.example.hungryist.databinding.ActivityMapsBinding
import com.example.hungryist.ui.activity.detailedinfo.DetailedInfoViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapsActivity : AppCompatActivity() {

    private val callback = OnMapReadyCallback { googleMap ->
        viewModel.detailedInfo.value?.let {

            val location = LatLng(it.geoPoint.latitude, it.geoPoint.longitude)
            val customMarkerIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_marker)
            val customMarker = BitmapDescriptorFactory.fromBitmap(customMarkerIcon)

            val marker = googleMap.addMarker(
                MarkerOptions().position(location).title(it.name).icon(customMarker)
            )
            marker?.showInfoWindow()
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f))

        }
    }

    @Inject
    lateinit var viewModel: DetailedInfoViewModel

    private val binding by lazy {
        ActivityMapsBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    companion object {
        fun intentFor(context: Context) {
            val intent = Intent(context, MapsActivity::class.java)
            context.startActivity(intent)
        }
    }
}