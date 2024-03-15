package com.example.hungryist.ui.activity.searchlocation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryist.R
import com.example.hungryist.adapter.SelectMapPlaceAdapter

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.example.hungryist.databinding.ActivitySearchLocationBinding
import com.example.hungryist.utils.extension.showToastMessage
import com.example.hungryist.utils.extension.triggerAnimatedVisibility
import com.example.hungryist.utils.extension.triggerVisibility
import com.example.hungryist.utils.mapsearchplace.MapSearchPlaceUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivitySearchLocationBinding
    private var currentLocation: LatLng? = null

    @Inject
    lateinit var viewModel: SearchLocationViewModel

    private val selectMapPlaceAdapter by lazy {
        SelectMapPlaceAdapter(mutableListOf()) {
            viewModel.placeSelectedActionMap(it, searchPlaceUtils)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getCurrentLocation()
            } else {
                this.showToastMessage(getString(R.string.permission_denied_message))
            }
        }


    private val searchPlaceUtils by lazy {
        MapSearchPlaceUtils(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViews()
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.selectedLocation.observe(this) {
            binding.editText.setText(it?.placeName)
            binding.recyclerView.triggerVisibility(false)
        }
    }

    private fun setViews() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setAdapter()
    }

    private fun setAdapter() {
        binding.recyclerView.apply {
            adapter = selectMapPlaceAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@SearchLocationActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            layoutManager = LinearLayoutManager(
                this@SearchLocationActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun setListeners() {
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnAdd.setOnClickListener {
            if (binding.editText.text.toString().isEmpty())
                binding.editText.error = getString(R.string.empty_location_info)
            else {
                viewModel.validatePlaceSelection(binding.editText.text.toString(), searchPlaceUtils)
                finish()
            }
        }

        binding.btnCurrentLocation.setOnClickListener {
            viewModel.setCurrentLocationClicked(searchPlaceUtils, currentLocation, this)
            finish()
        }

        binding.editText.addTextChangedListener {
            binding.recyclerView.triggerVisibility(it.toString().isNotEmpty())
            searchPlaceUtils.searchPlaces(it.toString()) {
                selectMapPlaceAdapter.update(it.toMutableList())
            }
        }

        binding.editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                binding.recyclerView.triggerVisibility(false)
        }

    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )
            showToastMessage(getString(R.string.permission_denied_message))
        else
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        viewModel.addMarker(currentLocation!!, R.drawable.ic_current_location)
                    } else {
                        showToastMessage("Location data not available")
                    }
                }
                .addOnFailureListener { e ->
                    showToastMessage("Error getting location: ${e.message}")
                }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.mMap = googleMap
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    companion object {
        fun intentFor(context: Context) {
            val intent = Intent(context, SearchLocationActivity::class.java)
            context.startActivity(intent)
        }
    }
}