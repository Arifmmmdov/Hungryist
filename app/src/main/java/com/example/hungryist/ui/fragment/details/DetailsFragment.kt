package com.example.hungryist.ui.fragment.details

import android.animation.ObjectAnimator
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryist.R
import com.example.hungryist.adapter.OpenCloseRecyclerAdapter
import com.example.hungryist.adapter.RatingRecyclerAdapter
import com.example.hungryist.databinding.FragmentDetailsBinding
import com.example.hungryist.model.DetailedInfoModel
import com.example.hungryist.model.OpenCloseTimes
import com.example.hungryist.model.RatingModel
import com.example.hungryist.ui.activity.detailedinfo.DetailedInfoViewModel
import com.example.hungryist.utils.extension.triggerVisibility
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: DetailedInfoViewModel
    private var ratingRotationAngle = 0.0f
    private var dateRotationAngle = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setObservers()
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.layoutExpandRating.setOnClickListener {
            setAnimatedRatingExpand()
        }

        binding.layoutExpandSchedule.setOnClickListener {
            setAnimatedDateExpand()
        }
    }

    private fun setAnimatedRatingExpand() {
        val anim: ObjectAnimator =
            ObjectAnimator.ofFloat(
                binding.icExpand, "rotation",
                ratingRotationAngle, ratingRotationAngle + 180
            )
        anim.setDuration(500)
        anim.start()
        ratingRotationAngle += 180
        ratingRotationAngle %= 360

        binding.recyclerRating.visibility =
            if (ratingRotationAngle == 0.0f) View.VISIBLE else View.GONE

    }

    private fun setAnimatedDateExpand() {
        val anim: ObjectAnimator =
            ObjectAnimator.ofFloat(
                binding.icExpandSchedule, "rotation",
                dateRotationAngle, dateRotationAngle + 180
            )
        anim.setDuration(500)
        anim.start()
        dateRotationAngle += 180
        dateRotationAngle %= 360

        binding.recyclerOpenDate.visibility =
            if (dateRotationAngle == 0.0f) View.VISIBLE else View.GONE
    }

    private fun setOpenCloseTimeAdapter(openCloseTimes: List<OpenCloseTimes>?) {
        openCloseTimes?.let { openCloseTimeList ->
            binding.recyclerOpenDate.adapter =
                OpenCloseRecyclerAdapter(requireContext(), openCloseTimeList)
            binding.recyclerOpenDate.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        binding.recyclerOpenDate.triggerVisibility(!openCloseTimes.isNullOrEmpty())
        binding.dividerRecyclerOpenDate.triggerVisibility(!openCloseTimes.isNullOrEmpty())
        binding.layoutExpandSchedule.triggerVisibility(!openCloseTimes.isNullOrEmpty())
    }

    private fun setRatingAdapter(ratingList: List<RatingModel>?) {
        ratingList?.let {
            binding.recyclerRating.adapter =
                RatingRecyclerAdapter(requireContext(), ratingList)
            binding.recyclerRating.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        binding.recyclerRating.triggerVisibility(!ratingList.isNullOrEmpty())
        binding.dividerRecyclerRating.triggerVisibility(!ratingList.isNullOrEmpty())
        binding.dividerRating.triggerVisibility(!ratingList.isNullOrEmpty())
        binding.layoutExpandRating.triggerVisibility(!ratingList.isNullOrEmpty())

    }

    private fun setObservers() {
        viewModel.detailedInfo.observe(requireActivity()) {
            setUpViews(it)
        }

        viewModel.openClosedDateList.observe(requireActivity()) {
            setOpenCloseTimeAdapter(it)
        }

        viewModel.ratingList.observe(requireActivity()) {
            setRatingAdapter(it)
        }
    }

    private fun setUpViews(info: DetailedInfoModel) {
        setVisibilities(info)

        performMapActions(info)
    }

    private fun performMapActions(info: DetailedInfoModel) {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            val location = com.google.android.gms.maps.model.LatLng(
                info.geoPoint.latitude,
                info.geoPoint.longitude
            )

            val customMarkerIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_marker)
            val customMarker = BitmapDescriptorFactory.fromBitmap(customMarkerIcon)

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            googleMap.addMarker(
                MarkerOptions().position(location).title(info.name).icon(customMarker)
            )
        }
    }

    private fun setVisibilities(info: DetailedInfoModel) {
        binding.freeWIFI.triggerVisibility(info.freeWifi)
        binding.bookingMandatory.triggerVisibility(info.bookingMandatory)
        binding.liveMusic.triggerVisibility(info.liveMusicEveryNight)
        binding.order.triggerVisibility(info.orderInAdvanced)
        binding.smokingArea.triggerVisibility(info.smokingArea)
        binding.studyingCondition.triggerVisibility(info.smokingArea)
    }
}