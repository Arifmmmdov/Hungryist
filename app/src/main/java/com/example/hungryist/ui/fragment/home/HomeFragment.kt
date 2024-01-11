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
import com.example.hungryist.adapter.FilteredInfoRecyclerAdapter
import com.example.hungryist.adapter.SelectedTextRecyclerAdapter
import com.example.hungryist.adapter.TopPlacesRecyclerAdapter
import com.example.hungryist.databinding.FragmentHomeBinding
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.SelectStringModel
import com.example.hungryist.utils.ItemDecoration
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
            changeDealsOfMonthVisibility(true)
        }

        viewModel.isTopPlacesLoading.observe(requireActivity()) {
            binding.recyclerInfo.visibility = if (it) View.GONE else View.VISIBLE
            binding.shimmerTopPlaces.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.isPlacesLoading.observe(requireActivity()) {
            binding.recyclerSelectPlaces.visibility = if (it) View.GONE else View.VISIBLE
            binding.shimmerSelectPlaces.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.baseInfoList.observe(requireActivity()) {
            setTopPlacesAdapter(it.filter { !it.titleName.isNullOrEmpty() }, false)
        }

        viewModel.filteredBaseInfoList.observe(requireActivity()) {
            if (it.isNotEmpty()) {
                setTopPlacesAdapter(it, true)
                changeDealsOfMonthVisibility(false)
            }
        }
    }

    private fun changeDealsOfMonthVisibility(visible: Boolean) {
        binding.dealsOfMonth.visibility = if (visible) View.VISIBLE else View.GONE
        binding.shimmerDealsOfMonth.visibility = if (visible) View.VISIBLE else View.GONE
        binding.recyclerDealsOfMonth.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun getItems() {

        viewModel.getPlaces {
            setSelectedTextAdapter(it.toMutableList())
        }

        viewModel.getBaseInfoModel()

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

    private fun setTopPlacesAdapter(places: List<BaseInfoModel>, isFiltered: Boolean) {
        val itemDecoration = ItemDecoration(resources.getDimensionPixelSize(R.dimen.item_space))
        binding.recyclerInfo.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter =
                if (isFiltered) FilteredInfoRecyclerAdapter(requireContext(), places, viewModel)
                else TopPlacesRecyclerAdapter(requireContext(), places, viewModel)

            addItemDecoration(itemDecoration)
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

        binding.editText.addTextChangedListener {
            viewModel.onTextTyped(it.toString())
        }
    }
}