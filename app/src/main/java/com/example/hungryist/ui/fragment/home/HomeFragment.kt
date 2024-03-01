package com.example.hungryist.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryist.R
import com.example.hungryist.adapter.DealsOfMonthAdapter
import com.example.hungryist.adapter.BaseInfoRecyclerAdapter
import com.example.hungryist.adapter.SelectedTextRecyclerAdapter
import com.example.hungryist.adapter.TopPlacesRecyclerAdapter
import com.example.hungryist.databinding.FragmentHomeBinding
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.SelectStringModel
import com.example.hungryist.ui.activity.filter.FilterActivity
import com.example.hungryist.utils.enum.VisibleStatusEnum
import com.example.hungryist.utils.extension.triggerVisibility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: HomeViewModel
    lateinit var selectedTextAdapter: SelectedTextRecyclerAdapter

    override fun onResume() {
        super.onResume()
        binding.lnrEmptyFilter.triggerVisibility(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        getItems()
        binding.lnrEmptyFilter.triggerVisibility(false)
        selectedTextAdapter =
            SelectedTextRecyclerAdapter(requireContext(), mutableListOf(), viewModel)
        setSelectedTextAdapter()
        setListeners()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.isDealsOfMonthLoading.observe(requireActivity()) {
            binding.dealsOfMonth.triggerVisibility(true)
            changeDealsOfMonthVisibility(if (it) VisibleStatusEnum.SHIMMER else VisibleStatusEnum.VISIBLE)
        }

        viewModel.isTopPlacesLoading.observe(requireActivity()) {
            changeTopPlacesVisibility(if (it) VisibleStatusEnum.SHIMMER else VisibleStatusEnum.VISIBLE)
        }

        viewModel.isPlacesLoading.observe(requireActivity()) {
            changeFilteredPlaceVisibility(if (it) VisibleStatusEnum.SHIMMER else VisibleStatusEnum.VISIBLE)
        }

        viewModel.places.observe(requireActivity()) {
            selectedTextAdapter.updateDataSet(it?: mutableListOf())
            checkCustomFilter()
        }

//        viewModel.baseInfoList.observe(requireActivity()) {
//            binding.lnrEmptyFilter.triggerVisibility(it.isEmpty())
//            setPlacesAdapter(it.filter { it.titleName.isNotEmpty() }, false)
//        }

        viewModel.filteredBaseInfoList.observe(requireActivity()) {
            if (viewModel.isFilterable) {
                changeDealsOfMonthVisibility(VisibleStatusEnum.INVISIBLE)
                binding.dealsOfMonth.triggerVisibility(false)
                setPlacesAdapter(it, true)
            } else {
                setPlacesAdapter(it.filter { it.titleName.isNotEmpty() }, false)
                viewModel.getDealsOfMonth {
                    setDealsOfMonthAdapter(it)
                }
            }
            binding.lnrEmptyFilter.triggerVisibility(it.isEmpty())
            checkCustomFilter()
        }
    }

    private fun checkCustomFilter() {
        if (viewModel.customApplied)
            selectedTextAdapter.addItem(SelectStringModel(getString(R.string.customFilter), true))
    }

    private fun changeFilteredPlaceVisibility(visibleStatusEnum: VisibleStatusEnum) {
        visibleStatusEnum.triggerVisibility(
            binding.shimmerSelectPlaces,
            binding.recyclerSelectPlaces
        )
    }

    private fun changeTopPlacesVisibility(visibleStatusEnum: VisibleStatusEnum) {
        visibleStatusEnum.triggerVisibility(binding.shimmerTopPlaces, binding.recyclerInfo)
    }

    private fun changeDealsOfMonthVisibility(visibleStatus: VisibleStatusEnum) {
        visibleStatus.triggerVisibility(binding.shimmerDealsOfMonth, binding.recyclerDealsOfMonth)
    }

    private fun getItems() {

        viewModel.getBaseList()

        if (!viewModel.isFilterable) {
            viewModel.getDealsOfMonth {
                setDealsOfMonthAdapter(it)
            }
        }

        viewModel.callPlaces()

    }

    private fun setSelectedTextAdapter() {
        binding.recyclerSelectPlaces.apply {
            adapter = selectedTextAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }
    }

    private fun setPlacesAdapter(places: List<BaseInfoModel>, isFiltered: Boolean) {
        binding.recyclerInfo.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter =
                if (isFiltered) BaseInfoRecyclerAdapter(requireContext(), places, viewModel)
                else TopPlacesRecyclerAdapter(requireContext(), places, viewModel)
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
            binding.lnrEmptyFilter.triggerVisibility(false)
            binding.swipeRefresh.isRefreshing = false
        }

        binding.editText.addTextChangedListener {
            viewModel.onTextTyped(it.toString())
        }

        binding.filterButton.setOnClickListener {
            FilterActivity.intentFor(requireContext())
        }

    }
}