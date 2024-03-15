package com.example.hungryist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hungryist.R
import com.example.hungryist.databinding.ItemOpenCloseTimesBinding
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder
import com.example.hungryist.model.RatingModel
import com.example.hungryist.utils.extension.triggerAnimatedVisibility
import com.example.hungryist.utils.extension.triggerVisibility

class RatingRecyclerAdapter(val context: Context, ratingList: List<RatingModel>) :
    BaseRecyclerAdapter<RatingModel, ItemOpenCloseTimesBinding>(ratingList.toMutableList()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding =
            ItemOpenCloseTimesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val itemBinding: ItemOpenCloseTimesBinding) :
        BaseViewHolder<RatingModel, ItemOpenCloseTimesBinding>(itemBinding) {


        val stars by lazy {
            listOf(
                itemBinding.star1,
                itemBinding.star2,
                itemBinding.star3,
                itemBinding.star4,
                itemBinding.star5
            )
        }

        override fun bind(item: RatingModel) {
            itemBinding.groupRating.triggerVisibility(true)
            itemBinding.dayName.text = item.name
            itemBinding.openCloseTimes.text = item.rate
            fillStars(item.rate)
        }

        private fun fillStars(rate: String) {
            try {
                val numberRate = rate.toDouble().toInt()
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