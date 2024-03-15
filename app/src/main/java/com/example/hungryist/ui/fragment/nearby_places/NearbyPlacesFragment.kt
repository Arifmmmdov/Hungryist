package com.example.hungryist.ui.fragment.nearby_places

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.FloatRange
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryist.R
import com.example.hungryist.adapter.BaseInfoRecyclerAdapter
import com.example.hungryist.adapter.SelectMapPlaceAdapter
import com.example.hungryist.databinding.FragmentNearbyPlacesBinding
import com.example.hungryist.ui.activity.searchlocation.SearchLocationActivity
import com.example.hungryist.ui.activity.searchlocation.SearchLocationViewModel
import com.example.hungryist.ui.dialog.ChooseLocationDialog
import com.example.hungryist.ui.fragment.home.HomeViewModel
import com.example.hungryist.utils.CustomTextWatcher
import com.example.hungryist.utils.extension.format
import com.example.hungryist.utils.extension.showToastMessage
import com.example.hungryist.utils.extension.triggerAnimatedVisibility
import com.example.hungryist.utils.extension.triggerVisibility
import com.example.hungryist.utils.mapsearchplace.MapSearchPlaceUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class NearbyPlacesFragment : Fragment() {

    private val binding by lazy {
        FragmentNearbyPlacesBinding.inflate(layoutInflater)
    }
    private var distanceRotationAngle = 0.0f
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val filteredAdapter by lazy {
        BaseInfoRecyclerAdapter(requireContext(), emptyList(),homeViewModel)
    }

    private val selectMapPlaceAdapter by lazy {
        SelectMapPlaceAdapter(mutableListOf()) {
            searchLocationViewModel.setPlaceSelected(it, searchPlaceUtils)
        }
    }

    private val searchPlaceUtils by lazy {
        MapSearchPlaceUtils(requireContext())
    }

    private val requestLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getCurrentLocation()
            } else {
                requireContext().showToastMessage(getString(R.string.permission_denied_message))
            }
        }

//    private val searchMapActivityResultLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        if (result.resultCode == RESULT_OK) {
//            val data: Intent? = result.data
//            val placeModel =
//                data?.getParcelableExtra<SearchMapPlaceModel>(SearchMapActivity.EXTRA_PLACE_MODEL)
//       }
//    }


    @Inject
    lateinit var nearbyViewModel: NearbyPlacesViewModel

    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var searchLocationViewModel: SearchLocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setViews()
        setListeners()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        nearbyViewModel.filteredList.observe(requireActivity()) {
            filteredAdapter.update(it)
        }

        searchLocationViewModel.selectedLocation.observe(requireActivity()) {
            if(it != null){
                nearbyViewModel.setPlaceSelected(it)
                binding.editText.setText(it.placeName)
            }
            binding.editText.clearFocus()
            binding.selectPlaceRecyclerView.triggerVisibility(false)
        }
    }

    private fun setListeners() {
        binding.layoutExpandDistance.setOnClickListener {
            setAnimatedDateExpand()
        }

        binding.sliderDistance.addOnChangeListener { slider, _, _ ->
            val from = slider.values[0].format()
            val to = slider.values[1].format()
            binding.distanceStart.text = getString(R.string.distance_m, from)
            binding.distanceEnd.text = getString(R.string.distance_m, to)
            nearbyViewModel.setDistanceSlider(FloatRange(from.toDouble(), to.toDouble()))
        }

        binding.sliderDistance.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                if (binding.editText.text.toString().isEmpty())
                    showChooseLocationDialog()
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
            }
        })


        binding.btnCafe.setOnClickListener {
            nearbyViewModel.setCafeSelected().also {
                binding.btnCafe.toggleSelection(it)
            }
        }

        binding.btnRestaurant.setOnClickListener {
            nearbyViewModel.setRestaurantSelected().also {
                binding.btnRestaurant.toggleSelection(it)
            }
        }

        binding.editText.onFocusChangeListener = OnFocusChangeListener { p0, focused ->
            if (!focused) binding.selectPlaceRecyclerView.triggerVisibility(false)
        }

        binding.editText.addTextChangedListener(CustomTextWatcher {
            if(it.isEmpty()){
                searchLocationViewModel.setPlaceSelected(null, searchPlaceUtils)
            }
            binding.selectPlaceRecyclerView.triggerVisibility(it.isNotEmpty())
            searchPlaceUtils.searchPlaces(it) {
                selectMapPlaceAdapter.update(it.toMutableList())
            }
        })

        binding.btnSearchLocation.setOnClickListener {
            SearchLocationActivity.intentFor(requireContext())
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    searchLocationViewModel.setCurrentLocationClicked(
                        searchPlaceUtils,
                        currentLocation,
                        requireContext()
                    )
                } else {
                    requireContext().showToastMessage("Location data not available")
                }
            }
            .addOnFailureListener { e ->
                requireContext().showToastMessage("Error getting location: ${e.message}")
            }
    }

    private fun showChooseLocationDialog() {
        val onChooseFromMapClicked = {
            SearchLocationActivity.intentFor(requireContext())
        }

        val onCurrentLocationClicked = {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        ChooseLocationDialog(
            requireContext(),
            onChooseFromMapClicked,
            onCurrentLocationClicked
        ).show()
    }

    private fun setViews() {
        binding.filteredRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = filteredAdapter
        }

        binding.selectPlaceRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = selectMapPlaceAdapter
        }

        val nearbyFilterInfo = nearbyViewModel.getInfo()
        val distanceStartValue = nearbyFilterInfo.distanceRange?.from?.toFloat() ?: 0f
        val distanceEndValue = nearbyFilterInfo.distanceRange?.to?.toFloat() ?: 1500f

        binding.sliderDistance.values = listOf(distanceStartValue, distanceEndValue).also {
            binding.distanceStart.text = getString(R.string.distance_m, it[0].format())
            binding.distanceEnd.text = getString(R.string.distance_m, it[1].format())
        }

        binding.editText.setText(nearbyFilterInfo.selectedPlace?.placeName)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.btnRestaurant.toggleSelection(nearbyFilterInfo.isRestaurantSelected)
        binding.btnCafe.toggleSelection(nearbyFilterInfo.isCafeSelected)

        nearbyViewModel.setFullList(homeViewModel.getFullList())
    }

    private fun setAnimatedDateExpand() {
        val anim: ObjectAnimator = ObjectAnimator.ofFloat(
            binding.icExpandDistance, "rotation", distanceRotationAngle, distanceRotationAngle + 180
        )
        anim.setDuration(500)
        anim.start()
        distanceRotationAngle += 180
        distanceRotationAngle %= 360

        binding.sliderDistance.triggerAnimatedVisibility(distanceRotationAngle == 0.0f)
        binding.distanceLabel.triggerAnimatedVisibility(distanceRotationAngle == 0.0f)
    }

    private fun Button.toggleSelection(isSelected: Boolean) {
        setBackgroundColor(
            requireContext().getColor(
                if (isSelected) {
                    R.color.main_color
                } else {
                    R.color.card_background
                }
            )
        )
        setTextColor(
            requireContext().getColor(if (isSelected) R.color.white else R.color.black)
        )

    }


}