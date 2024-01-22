package com.example.hungryist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.hungryist.R
import com.example.hungryist.databinding.ItemRecyclerReviewsBinding
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder
import com.example.hungryist.model.ReviewsModel
import com.example.hungryist.utils.SpacesItemDecoration


class ReviewsAdapter(
    val fragmentManager: FragmentManager,
    val context: Context,
    dataList: List<ReviewsModel>,
) : BaseRecyclerAdapter<ReviewsModel, ItemRecyclerReviewsBinding>(dataList) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding =
            ItemRecyclerReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemRecyclerReviewsBinding) :
        BaseViewHolder<ReviewsModel, ItemRecyclerReviewsBinding>(binding) {
        private val stars by lazy {
            listOf(
                binding.star1,
                binding.star2,
                binding.star3,
                binding.star4,
                binding.star5
            )
        }

        override fun bind(item: ReviewsModel) {
            binding.name.text = item.customer
            binding.txtReviews.text = item.reply.toString()
            Glide.with(context).load(item.profile).into(binding.profilePhoto)
            fillStars(item.rate)
            binding.rating.text = item.rate.toString()
            binding.txtWroteOn.text = item.wroteOn
            binding.description.text = item.message
            setInteriorDesign(item.images)
            binding.placeName.text = item.restaurantName
            binding.txtLike.text = item.like.toString()
            binding.txtDislike.text = item.dislike.toString()
        }

        private fun setInteriorDesign(images: List<String>) {
            binding.recyclerImages.apply {
                adapter = InteriorAdapter(context, images, 3)
                addItemDecoration(SpacesItemDecoration(8))
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }

        private fun fillStars(rate: Double) {
            try {
                val numberRate = rate.toInt()
                for (i in stars.indices) {
                    val filledDrawable =
                        if (i < numberRate) R.drawable.ic_filled_star else R.drawable.ic_unfilled_star
                    stars[i].setImageResource(filledDrawable)
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        }
    }

}