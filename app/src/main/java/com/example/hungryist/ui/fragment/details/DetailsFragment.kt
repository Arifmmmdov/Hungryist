package com.example.hungryist.ui.fragment.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hungryist.databinding.FragmentDetailsBinding
import com.example.hungryist.model.DetailedInfoModel
import com.example.hungryist.ui.activity.detailedinfo.DetailedInfoViewModel
import com.example.hungryist.utils.extension.triggerVisibility
import javax.inject.Inject

class DetailsFragment : Fragment() {
    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: DetailedInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.detailedInfo.observe(requireActivity()) {
            setUpViews(it)
        }
    }

    private fun setUpViews(info: DetailedInfoModel) {
        binding.freeWIFI.triggerVisibility(info.freeWifi)
        binding.bookingMandatory.triggerVisibility(info.bookingMandatory)
        binding.liveMusic.triggerVisibility(info.liveMusicEveryNight)
        binding.order.triggerVisibility(info.orderInAdvanced)
        binding.smokingArea.triggerVisibility(info.smokingArea)
        binding.studyingCondition.triggerVisibility(info.smokingArea)
    }
}