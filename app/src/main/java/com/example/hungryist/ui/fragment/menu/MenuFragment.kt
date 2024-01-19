package com.example.hungryist.ui.fragment.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryist.R
import com.example.hungryist.adapter.FilteredInfoRecyclerAdapter
import com.example.hungryist.adapter.MenuRecyclerAdapter
import com.example.hungryist.adapter.SelectedTextRecyclerAdapter
import com.example.hungryist.adapter.TopPlacesRecyclerAdapter
import com.example.hungryist.databinding.FragmentMenuBinding
import com.example.hungryist.model.SelectStringModel
import com.example.hungryist.ui.activity.detailedinfo.DetailedInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment() {

    val binding by lazy {
        FragmentMenuBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: DetailedInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        setTypeSelectedRecycler()
        setMenuRecycler()
    }

    private fun setMenuRecycler() {
        binding.recyclerMenu.run {
            adapter =
                viewModel.detailedInfo.value?.menuList?.let {
                    MenuRecyclerAdapter(
                        requireContext(),
                        it
                    )
                }
            addItemDecoration(DividerItemDecoration(this.context,DividerItemDecoration.VERTICAL))
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setTypeSelectedRecycler() {
        binding.recyclerSelectTypes.run {
            adapter =
                SelectedTextRecyclerAdapter(requireContext(), getTypes()) {
                    //TODO perform action while type is selected
                }
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun getTypes(): MutableList<SelectStringModel> {
        return mutableListOf(
            SelectStringModel("Appetizer"),
            SelectStringModel("Soup"),
            SelectStringModel("Wings"),
            SelectStringModel("Seafood")
        )
    }

}