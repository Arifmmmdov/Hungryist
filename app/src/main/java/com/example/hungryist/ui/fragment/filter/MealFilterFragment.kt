package com.example.hungryist.ui.fragment.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryist.R
import com.example.hungryist.adapter.SelectMealRecyclerAdapter
import com.example.hungryist.adapter.SimpleTextRecyclerAdapter
import com.example.hungryist.databinding.FragmentMealFilterBinding
import com.example.hungryist.model.MealFilterModel
import com.example.hungryist.model.MealModel
import com.example.hungryist.ui.fragment.home.HomeViewModel
import com.example.hungryist.utils.extension.triggerVisibility
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MealFilterFragment : Fragment() {

    private val binding by lazy {
        FragmentMealFilterBinding.inflate(layoutInflater)
    }

    private val selectMenuAdapter by lazy {
        SelectMealRecyclerAdapter(viewModel.getMealNames()) {
            binding.editText.setText(it)
            binding.editText.clearFocus()
        }
    }

    @Inject
    lateinit var viewModel: HomeViewModel

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

        binding.btnApplyFilter.setOnClickListener {
            viewModel.filterMeals(getFilterModel())
            requireActivity().finish()
        }

        binding.editText.addTextChangedListener { editable ->
            selectMenuAdapter.filter.filter(editable.toString())
        }

        binding.editText.setOnFocusChangeListener { view, b ->
            binding.recyclerView.triggerVisibility(b)
        }
    }

    private fun getFilterModel(): MealFilterModel {
        return MealFilterModel(
            binding.editText.text.toString(),
            IntRange(binding.sliderPrice.values[0].toInt(), binding.sliderPrice.values[1].toInt())
        )
    }

    private fun setViews() {
        val filterMeal = viewModel.getFilterMeal()

        val priceStart = filterMeal?.priceRange?.first?.toFloat() ?: 0f
        val priceEnd = filterMeal?.priceRange?.last?.toFloat() ?: 1000f
        binding.sliderPrice.values = listOf(priceStart, priceEnd).also {
            binding.priceStart.text = getString(R.string.price_azn, it[0].toInt())
            binding.priceEnd.text = getString(R.string.price_azn, it[1].toInt())
        }

        binding.editText.setText(filterMeal?.name ?: "")
        setRecyclerAdapter()
    }

    private fun setRecyclerAdapter() {
        binding.recyclerView.apply {
            adapter = selectMenuAdapter
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
}