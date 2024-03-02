package com.example.hungryist.ui.fragment.filter

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.FloatRange
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryist.R
import com.example.hungryist.adapter.SelectMapPlaceAdapter
import com.example.hungryist.databinding.FragmentPlaceFilterBinding
import com.example.hungryist.model.PlaceFilterModel
import com.example.hungryist.model.SearchMapPlaceModel
import com.example.hungryist.ui.activity.searchlocation.SearchLocationActivity
import com.example.hungryist.ui.activity.searchlocation.SearchLocationViewModel
import com.example.hungryist.ui.fragment.home.HomeViewModel
import com.example.hungryist.utils.CustomTextWatcher
import com.example.hungryist.utils.extension.format
import com.example.hungryist.utils.extension.triggerVisibility
import com.example.hungryist.utils.mapsearchplace.MapSearchPlaceUtils
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PlaceFilterFragment : Fragment() {

    private val binding by lazy {
        FragmentPlaceFilterBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var searchLocationViewModel: SearchLocationViewModel

    private val selectMapPlaceAdapter by lazy {
        SelectMapPlaceAdapter(mutableListOf()) {
            searchLocationViewModel.setPlaceSelected(it, searchPlaceUtils)
        }
    }

    private var restaurantSelected: Boolean = true
    private var cafeSelected: Boolean = true
    private var distanceRotationAngle = 180.0f
    private var priceRotationAngle = 180.0f

    private val searchPlaceUtils by lazy {
        MapSearchPlaceUtils(requireContext())
    }


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
        searchLocationViewModel.selectedLocation.observe(requireActivity()) {
            binding.editText.setText(it.placeName)
            binding.recyclerView.triggerVisibility(false)
        }
    }

    private fun setListeners() {
        binding.btnApplyFilter.setOnClickListener {
            homeViewModel.filterPlaces(getFilterItems())
            requireActivity().finish()
        }

        binding.layoutExpandDistance.setOnClickListener {
            setAnimatedDistanceExpand()
        }

        binding.layoutExpandPrice.setOnClickListener {
            setAnimatedPriceExpand()
        }

        binding.sliderDistance.addOnChangeListener(RangeSlider.OnChangeListener { slider, value, fromUser ->
            binding.distanceStart.text =
                getString(R.string.distance_m, slider.values[0].toFloat().format())
            binding.distanceEnd.text = getString(R.string.distance_m, slider.values[1].format())
        })

        binding.sliderPrice.addOnChangeListener(RangeSlider.OnChangeListener { slider, value, fromUser ->
            binding.priceStart.text = getString(R.string.price_azn, slider.values[0].toInt())
            binding.priceEnd.text = getString(R.string.price_azn, slider.values[1].toInt())
        })

        binding.btnRestaurant.apply {
            setOnClickListener {
                restaurantSelected = !restaurantSelected
                toggleSelection(restaurantSelected)
            }
        }

        binding.btnCafe.apply {
            setOnClickListener {
                cafeSelected = !cafeSelected
                toggleSelection(cafeSelected)
            }
        }

        binding.btnSearchLocation.setOnClickListener {
            SearchLocationActivity.intentFor(requireContext())
        }

        binding.editText.addTextChangedListener(CustomTextWatcher {
            binding.recyclerView.triggerVisibility(it.isNotEmpty())
            searchPlaceUtils.searchPlaces(it) {
                selectMapPlaceAdapter.update(it.toMutableList())
            }
        })

        binding.editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) binding.recyclerView.triggerVisibility(false)
        }
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

    private fun getFilterItems(): PlaceFilterModel {
        return PlaceFilterModel(
            restaurantSelected,
            cafeSelected,
            searchLocationViewModel.selectedLocation.value,
            FloatRange(
                binding.sliderDistance.values[0].toDouble(),
                binding.sliderDistance.values[1].toDouble()
            ),
            IntRange(
                binding.sliderPrice.values[0].toInt(), binding.sliderPrice.values[1].toInt()
            )
        )
    }

    private fun setViews() {
        val filterPlace = homeViewModel.getFilterPlace()
        val distanceStartValue = filterPlace?.distanceRange?.from?.toFloat() ?: 0f
        val distanceEndValue = filterPlace?.distanceRange?.to?.toFloat() ?: 1000f

        binding.sliderDistance.values = listOf(distanceStartValue, distanceEndValue).also {
            binding.distanceStart.text = getString(R.string.distance_m, it[0].format())
            binding.distanceEnd.text = getString(R.string.distance_m, it[1].format())
        }

        filterPlace?.location?.let { searchLocationViewModel.setPlaceSelected(it) }

        val priceStartValue = filterPlace?.priceRange?.first?.toFloat() ?: 0f
        val priceEndValue = filterPlace?.priceRange?.last?.toFloat() ?: 800f
        binding.sliderPrice.values = listOf(priceStartValue, priceEndValue).also {
            binding.priceStart.text = getString(R.string.price_azn, it[0].toInt())
            binding.priceEnd.text = getString(R.string.price_azn, it[1].toInt())
        }

        cafeSelected = filterPlace?.isCafe ?: true
        restaurantSelected = filterPlace?.isRestaurant ?: true

        binding.btnCafe.toggleSelection(cafeSelected)
        binding.btnRestaurant.toggleSelection(restaurantSelected)

        setAdapter()
        setAnimatedDistanceExpand()
        setAnimatedPriceExpand()
    }

    private fun setAdapter() {
        binding.recyclerView.apply {
            adapter = selectMapPlaceAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun setAnimatedDistanceExpand() {
        val anim: ObjectAnimator = ObjectAnimator.ofFloat(
            binding.icExpandDistance, "rotation", distanceRotationAngle, distanceRotationAngle + 180
        )
        anim.setDuration(250)
        anim.start()
        distanceRotationAngle += 180
        distanceRotationAngle %= 360

        binding.sliderDistance.triggerVisibility(distanceRotationAngle == 0.0f)
        binding.distanceLabel.triggerVisibility(distanceRotationAngle == 0.0f)
    }

    private fun setAnimatedPriceExpand() {
        val anim: ObjectAnimator = ObjectAnimator.ofFloat(
            binding.icExpandPrice, "rotation", priceRotationAngle, priceRotationAngle + 180
        )
        anim.setDuration(250)
        anim.start()
        priceRotationAngle += 180
        priceRotationAngle %= 360

        binding.sliderPrice.triggerVisibility(priceRotationAngle == 0.0f)
        binding.priceLabel.triggerVisibility(priceRotationAngle == 0.0f)
    }


}