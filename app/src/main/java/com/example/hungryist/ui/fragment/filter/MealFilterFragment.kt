package com.example.hungryist.ui.fragment.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hungryist.R
import com.example.hungryist.databinding.FragmentMealFilterBinding
import com.google.android.material.slider.RangeSlider

class MealFilterFragment : Fragment() {

    private val binding by lazy {
        FragmentMealFilterBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setViews()
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.sliderPrice.addOnChangeListener(RangeSlider.OnChangeListener { slider, _, _ ->
            binding.priceStart.text =
                getString(R.string.price_azn, slider.values[0].toInt())
            binding.priceEnd.text = getString(R.string.price_azn, slider.values[1].toInt())
        })
    }

    private fun setViews() {
        binding.sliderPrice.values = listOf(0f, 1000f).also {
            binding.priceStart.text = getString(R.string.price_azn, it[0].toInt())
            binding.priceEnd.text = getString(R.string.price_azn, it[1].toInt())
        }
    }
}