package com.example.hungryist.ui.fragment.reviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryist.adapter.ReviewsAdapter
import com.example.hungryist.databinding.FragmentReviewsBinding
import com.example.hungryist.model.ReviewsModel
import com.example.hungryist.utils.enum.VisibleStatusEnum
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReviewsFragment : Fragment() {

    val binding by lazy {
        FragmentReviewsBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: ReviewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel.getReviews()
        setObservers()
        setListener()
        setVisibility(VisibleStatusEnum.SHIMMER)
        return binding.root
    }

    private fun setListener() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getReviews()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setObservers() {
        viewModel.reviewsList.observe(requireActivity()) {
            setRecyclerReviews(it)
        }
    }

    private fun setRecyclerReviews(reviewsList: List<ReviewsModel>?) {
        binding.recyclerReviews.apply {
            adapter = reviewsList?.let { ReviewsAdapter(parentFragmentManager,requireContext(), it) }
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        setVisibility(if (reviewsList.isNullOrEmpty()) VisibleStatusEnum.INVISIBLE else VisibleStatusEnum.VISIBLE)
    }

    private fun setVisibility(visibilityStatus: VisibleStatusEnum) {
        visibilityStatus.triggerVisibility(binding.shimmerReviews, binding.recyclerReviews)
    }

}