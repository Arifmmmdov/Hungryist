package com.example.hungryist.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryist.adapter.SelectedTextRecyclerAdapter
import com.example.hungryist.databinding.FragmentHomeBinding
import com.example.hungryist.databinding.ItemRecyclerFilterBinding
import com.example.hungryist.model.SelectStringModel

class HomeFragment : Fragment() {
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setListeners()
        setRecyclerViews()
        return binding.root
    }

    private fun setRecyclerViews() {
        binding.recyclerFilter.apply {
            adapter = SelectedTextRecyclerAdapter(requireContext(), getPlacesList())
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun getPlacesList(): List<SelectStringModel> {
        return listOf(
            SelectStringModel("Restaurants", false),
            SelectStringModel("Cafés", false),
            SelectStringModel("Reviews", false),
        )
    }

    private fun setListeners() {

    }
}