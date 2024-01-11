package com.example.hungryist.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryist.adapter.DealsOfMonthAdapter
import com.example.hungryist.adapter.SelectedTextRecyclerAdapter
import com.example.hungryist.adapter.TopPlacesRecyclerAdapter
import com.example.hungryist.databinding.FragmentHomeBinding
import com.example.hungryist.model.SelectStringModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setListeners()
        setRecyclerViews()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.isDealsOfMonthLoading.observe(requireActivity()) {
            binding.recyclerDealsOfMonth.visibility = if (it) View.GONE else View.VISIBLE
            binding.shimmerDealsOfMonth.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.isTopPlacesLoading.observe(requireActivity()) {
            binding.recyclerTop.visibility = if (it) View.GONE else View.VISIBLE
            binding.shimmerTopPlaces.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun setRecyclerViews() {
        binding.recyclerFilter.apply {
            adapter =
                SelectedTextRecyclerAdapter(requireContext(), getPlacesList(), viewModel)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.recyclerTop.apply {
            viewModel.getBaseInfoModel {
                adapter = TopPlacesRecyclerAdapter(requireContext(), it, viewModel)
            }
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        binding.recyclerDealsOfMonth.apply {
            viewModel.getDealsOfMonth {
                adapter = DealsOfMonthAdapter(requireContext(), it)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun getPlacesList(): MutableList<SelectStringModel> {
        return mutableListOf(
            SelectStringModel("Restaurants", false),
            SelectStringModel("Cafés", false),
            SelectStringModel("Reviews", false),
        )
    }

    private fun setListeners() {

    }
}