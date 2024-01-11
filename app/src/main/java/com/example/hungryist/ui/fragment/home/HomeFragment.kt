package com.example.hungryist.ui.fragment.home

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
import com.example.hungryist.model.BaseInfoModel
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
        getItems()
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

        viewModel.isPlacesLoading.observe(requireActivity()) {
            binding.recyclerSelectPlaces.visibility = if (it) View.GONE else View.VISIBLE
            binding.shimmerSelectPlaces.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun getItems() {

        viewModel.getPlaces {
            setSelectedTextAdapter(it.toMutableList())
        }

        viewModel.getBaseInfoModel {
            setTopPlacesAdapter(it)
        }
        viewModel.getDealsOfMonth {
            setDealsOfMonthAdapter(it)
        }

    }

    private fun setSelectedTextAdapter(places: MutableList<SelectStringModel>) {

        binding.recyclerSelectPlaces.apply {
            adapter =
                SelectedTextRecyclerAdapter(requireContext(), places, viewModel)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setTopPlacesAdapter(topPlaces: List<BaseInfoModel>) {
        binding.recyclerTop.apply {
            adapter = TopPlacesRecyclerAdapter(requireContext(), topPlaces, viewModel)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setDealsOfMonthAdapter(images: List<String>) {
        binding.recyclerDealsOfMonth.apply {
            adapter = DealsOfMonthAdapter(requireContext(), images)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }


    private fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            getItems()
            binding.swipeRefresh.isRefreshing = false
        }
    }
}