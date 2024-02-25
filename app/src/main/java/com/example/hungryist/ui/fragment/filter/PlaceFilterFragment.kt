package com.example.hungryist.ui.fragment.filter

import android.animation.ObjectAnimator
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.hungryist.R
import com.example.hungryist.databinding.FragmentPlaceFilterBinding
import com.example.hungryist.model.PlaceFilterModel
import com.example.hungryist.ui.fragment.home.HomeViewModel
import com.example.hungryist.utils.extension.format
import com.example.hungryist.utils.extension.triggerVisibility
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PlaceFilterFragment : Fragment() {

    private val binding by lazy {
        FragmentPlaceFilterBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: HomeViewModel

    private var restaurantSelected: Boolean = true
    private var cafeSelected: Boolean = true
    private var distanceRotationAngle = 180.0f
    private var priceRotationAngle = 180.0f


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setViews()
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.btnApplyFilter.setOnClickListener {
            viewModel.filterPlaces(getFilterItems())
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
            binding.priceStart.text =
                getString(R.string.price_azn, slider.values[0].toInt())
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
            restaurantSelected, binding.editText.text.toString(), IntRange(
                binding.sliderDistance.values[0].toInt(), binding.sliderDistance.values[1].toInt()
            ), IntRange(
                binding.sliderPrice.values[0].toInt(), binding.sliderPrice.values[1].toInt()
            )
        )
    }

    private fun setViews() {
        binding.sliderDistance.values = listOf(0f, 1000f).also {
            binding.distanceStart.text = getString(R.string.distance_m, it[0].format())
            binding.distanceEnd.text = getString(R.string.distance_m, it[1].format())
        }
        binding.sliderPrice.values = listOf(0f, 800f).also {
            binding.priceStart.text = getString(R.string.price_azn, it[0].toInt())
            binding.priceEnd.text = getString(R.string.price_azn, it[1].toInt())
        }
        setAnimatedDistanceExpand()
        setAnimatedPriceExpand()
    }

    private fun setAnimatedDistanceExpand() {
        val anim: ObjectAnimator = ObjectAnimator.ofFloat(
            binding.icExpandDistance, "rotation", distanceRotationAngle, distanceRotationAngle + 180
        )
        anim.setDuration(500)
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
        anim.setDuration(500)
        anim.start()
        priceRotationAngle += 180
        priceRotationAngle %= 360

        binding.sliderPrice.triggerVisibility(priceRotationAngle == 0.0f)
        binding.priceLabel.triggerVisibility(priceRotationAngle == 0.0f)
    }


}