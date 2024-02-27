package com.example.hungryist.ui.fragment.menu

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryist.R
import com.example.hungryist.adapter.MenuRecyclerAdapter
import com.example.hungryist.adapter.SelectedTextRecyclerAdapter
import com.example.hungryist.databinding.FragmentMenuBinding
import com.example.hungryist.model.MenuModel
import com.example.hungryist.model.SelectStringModel
import com.example.hungryist.utils.*
import com.example.hungryist.utils.enum.VisibleStatusEnum
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MenuFragment : Fragment() {

    val binding by lazy {
        FragmentMenuBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: MenuViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setUpViews()
        viewModel.getData()
        setObservers()
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.editText.addTextChangedListener {
            viewModel.filterForTyped(it.toString())
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getMenuList()
            binding.swipeRefresh.isRefreshing = false
            resetViews()
        }
    }

    private fun resetViews() {
        binding.editText.setText("")

    }

    private fun setObservers() {
        viewModel.menuList.observe(requireActivity()) {
            setMenuRecycler(it)
        }

        viewModel.categoriesList.observe(requireActivity()) {
            setTypeSelectedRecycler(it)
        }
    }

    private fun setUpViews() {
        triggerRecyclerMenuVisibility(VisibleStatusEnum.SHIMMER)
        triggerSelectedTypesVisibility(VisibleStatusEnum.SHIMMER)
    }

    private fun setMenuRecycler(menuList: List<MenuModel>) {
        binding.recyclerMenu.run {
            adapter = MenuRecyclerAdapter(requireContext(), menuList)
            val itemDecoration =
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.grey
                    )
                )
            )
            addItemDecoration(itemDecoration)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        triggerRecyclerMenuVisibility(if (menuList.isNotEmpty()) VisibleStatusEnum.VISIBLE else VisibleStatusEnum.INVISIBLE)
    }

    private fun triggerRecyclerMenuVisibility(visibleStatus: VisibleStatusEnum) {
        visibleStatus.triggerVisibility(binding.shimmerMenu, binding.recyclerMenu)
    }

    private fun triggerSelectedTypesVisibility(visibleStatus: VisibleStatusEnum) {
        visibleStatus.triggerVisibility(binding.shimmerSelectPlaces, binding.recyclerSelectTypes)
    }

    private fun setTypeSelectedRecycler(categoriesList: List<SelectStringModel>) {
        binding.recyclerSelectTypes.run {
            adapter =
                SelectedTextRecyclerAdapter(requireContext(), categoriesList.toMutableList(),viewModel)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        triggerSelectedTypesVisibility(if (categoriesList.isEmpty()) VisibleStatusEnum.INVISIBLE else VisibleStatusEnum.VISIBLE)
    }

}