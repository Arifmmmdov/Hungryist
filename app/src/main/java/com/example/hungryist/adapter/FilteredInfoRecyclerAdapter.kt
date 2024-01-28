package com.example.hungryist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hungryist.R
import com.example.hungryist.databinding.ItemRecyclerDetailedInfoBinding
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.ui.activity.detailedinfo.DetailedInfoActivity
import com.example.hungryist.ui.fragment.home.HomeViewModel
import com.example.hungryist.utils.RestaurantStatusChecker
import com.example.hungryist.utils.extension.setSaved
import com.example.hungryist.utils.extension.setStatus
import com.example.hungryist.utils.extension.triggerVisibility
import javax.inject.Inject

class FilteredInfoRecyclerAdapter @Inject constructor(
    val context: Context,
    dataList: List<BaseInfoModel>,
    val viewModel: HomeViewModel,
) :
    BaseRecyclerAdapter<BaseInfoModel, ItemRecyclerDetailedInfoBinding>(dataList) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<BaseInfoModel, ItemRecyclerDetailedInfoBinding> {
        val binding = ItemRecyclerDetailedInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemRecyclerDetailedInfoBinding) :
        BaseViewHolder<BaseInfoModel, ItemRecyclerDetailedInfoBinding>(binding) {
        override fun bind(item: BaseInfoModel) {
            setVisibilities(item)
            binding.run {
                name.text = item.name
                val statusColor = openStatus.setStatus(item.openCloseTimes)
                binding.dot.setBackgroundColor(context.getColor(statusColor))
                savedSticker.setSaved()
                location.text = item.location
                openEndTime.text =
                    RestaurantStatusChecker.getTimesToday(context, item.openCloseTimes)
                rate.text = item.overallRating.toString()
                reviews.text =
                    context.resources.getString(R.string.reviews, item.reviews)

                Glide.with(context).load(item.baseImage).into(mainImage)
            }
        }

        private fun setVisibilities(item: BaseInfoModel) {
            binding.reviews.triggerVisibility(item.reviews.isNotEmpty())
            binding.imageReviews.triggerVisibility(item.reviews.isNotEmpty())
            binding.starLight.triggerVisibility(item.overallRating != 0.0)
            binding.rate.triggerVisibility(item.overallRating != 0.0)
        }

        override fun clickListener(position: Int) {
            binding.savedSticker.setOnClickListener {
                dataList[position].apply {
//                    this.saved = !this.saved
//                    viewModel.setSavedInfo(id,referenceId, saved)
                    notifyItemChanged(position)
                }
            }

            binding.itemFrame.setOnClickListener {
                DetailedInfoActivity.intentFor(context, dataList[position].id)
            }
        }
    }
}