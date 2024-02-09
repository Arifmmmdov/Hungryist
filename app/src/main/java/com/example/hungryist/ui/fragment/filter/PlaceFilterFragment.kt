package com.example.hungryist.ui.fragment.filter

import android.animation.ObjectAnimator
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hungryist.databinding.FragmentPlaceFilterBinding
import com.example.hungryist.model.PlaceFilterModel
import com.example.hungryist.ui.fragment.home.HomeViewModel
import com.example.hungryist.utils.extension.triggerVisibility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PlaceFilterFragment : Fragment() {

    private val binding by lazy {
        FragmentPlaceFilterBinding.inflate(layoutInflater)
    }

//    private val launcherActivity by lazy {
//
//    }

    @Inject
    lateinit var viewModel: HomeViewModel

    private var isRestaurant: Boolean = true
    private var distanceRotationAngle = 0f
    private var priceRotationAngle = 0f


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
    }

    private fun getFilterItems(): PlaceFilterModel {
        return PlaceFilterModel(
            isRestaurant,
            binding.editText.text.toString(),
            IntRange(
                binding.sliderDistance.values[0].toInt(),
                binding.sliderDistance.values[1].toInt()
            ),
            IntRange(
                binding.sliderPrice.values[0].toInt(),
                binding.sliderPrice.values[1].toInt()
            )
        )
    }

    private fun setViews() {
        binding.sliderDistance.values = listOf(200f, 800f)
        binding.sliderPrice.values = listOf(50f, 550f)
        setAnimatedDistanceExpand()
        setAnimatedPriceExpand()
    }

    private fun setAnimatedDistanceExpand() {
        val anim: ObjectAnimator =
            ObjectAnimator.ofFloat(
                binding.icExpandDistance, "rotation",
                distanceRotationAngle, distanceRotationAngle + 180
            )
        anim.setDuration(500)
        anim.start()
        distanceRotationAngle += 180
        distanceRotationAngle %= 360

        binding.sliderDistance.triggerVisibility(distanceRotationAngle == 0.0f)
    }

    private fun setAnimatedPriceExpand() {
        val anim: ObjectAnimator =
            ObjectAnimator.ofFloat(
                binding.icExpandDistance, "rotation",
                priceRotationAngle, priceRotationAngle + 180
            )
        anim.setDuration(500)
        anim.start()
        priceRotationAngle += 180
        priceRotationAngle %= 360

        binding.sliderPrice.triggerVisibility(priceRotationAngle == 0.0f)
    }


}