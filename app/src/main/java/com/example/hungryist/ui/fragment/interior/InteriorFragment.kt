package com.example.hungryist.ui.fragment.interior

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hungryist.adapter.InteriorAdapter
import com.example.hungryist.databinding.FragmentInteriorBinding
import com.example.hungryist.ui.activity.detailedinfo.DetailedInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InteriorFragment : Fragment() {

    val binding by lazy {
        FragmentInteriorBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: DetailedInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setInteriorRecycler(viewModel.interiorList.value)
        setObservers()
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getDetailedInfo()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setObservers() {
        viewModel.interiorList.observe(requireActivity()) {
            setInteriorRecycler(it)
        }
    }

    private fun setInteriorRecycler(value: List<String>?) {

        binding.recyclerInterior.apply {
            adapter =
                value?.let { InteriorAdapter(parentFragmentManager, requireContext(), it, 9) }
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        }
    }
}