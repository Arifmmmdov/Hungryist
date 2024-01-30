package com.example.hungryist.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryist.adapter.DealsOfMonthAdapter
import com.example.hungryist.adapter.BaseInfoRecyclerAdapter
import com.example.hungryist.adapter.SelectedTextRecyclerAdapter
import com.example.hungryist.adapter.TopPlacesRecyclerAdapter
import com.example.hungryist.databinding.FragmentHomeBinding
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.SelectStringModel
import com.example.hungryist.utils.enum.VisibleStatusEnum
import com.example.hungryist.utils.extension.triggerVisibility
import com.example.hungryist.utils.filterutils.MainPageFilterUtils
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
        setObservers()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getItems()
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

        viewModel.baseInfoList.observe(requireActivity()) {
            setPlacesAdapter(it.filter { it.titleName.isNotEmpty() }, false)
            MainPageFilterUtils.setBaseInfoList(it)
        }

        viewModel.filteredBaseInfoList.observe(requireActivity()) {
            setPlacesAdapter(it, true)
            changeDealsOfMonthVisibility(VisibleStatusEnum.INVISIBLE)
            binding.dealsOfMonth.triggerVisibility(false)

        }
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

        viewModel.getPlaces {
            setSelectedTextAdapter(it.toMutableList())
        }

        viewModel.getBaseList()

        viewModel.getDealsOfMonth {
            setDealsOfMonthAdapter(it)
        }

    }

    private fun setSelectedTextAdapter(places: MutableList<SelectStringModel>) {

        binding.recyclerSelectPlaces.apply {
            adapter =
                SelectedTextRecyclerAdapter(requireContext(), places) {
                    viewModel.onTypeSelected(it)
                }
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
            binding.swipeRefresh.isRefreshing = false
            binding.editText.setText("")
        }

        binding.editText.addTextChangedListener {
            viewModel.onTextTyped(it.toString())
        }

    }
}