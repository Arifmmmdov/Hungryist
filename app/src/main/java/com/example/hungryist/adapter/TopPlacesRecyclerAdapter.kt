package com.example.hungryist.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.hungryist.R
import com.example.hungryist.databinding.ItemRecyclerTopPlacesBinding
import com.example.hungryist.ui.fragment.home.HomeViewModel
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.ui.activity.detailedinfo.DetailedInfoActivity
import com.example.hungryist.utils.RestaurantStatusChecker
import com.example.hungryist.utils.SharedPreferencesManager
import com.example.hungryist.utils.UserManager
import com.example.hungryist.utils.extension.setStatus
import com.example.hungryist.utils.extension.setSaved
import com.example.hungryist.utils.extension.triggerVisibility
import javax.inject.Inject

class TopPlacesRecyclerAdapter @Inject constructor(
    val context: Context,
    dataList: List<BaseInfoModel>,
    val viewModel: HomeViewModel,
) :
    BaseRecyclerAdapter<BaseInfoModel, ItemRecyclerTopPlacesBinding>(dataList) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<BaseInfoModel, ItemRecyclerTopPlacesBinding> {
        val binding = ItemRecyclerTopPlacesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemRecyclerTopPlacesBinding) :
        BaseViewHolder<BaseInfoModel, ItemRecyclerTopPlacesBinding>(binding) {
        override fun bind(item: BaseInfoModel) {
            setVisibilities(item)
            binding.title.text = item.titleName
            binding.itemRecyclerDetailedInfo.run {
                name.text = item.name
                val statusColor = openStatus.setStatus(item.openCloseTimes)
                dot.setCardBackgroundColor(ContextCompat.getColor(context, statusColor))
                savedSticker.setSaved(item.id)

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
            binding.itemRecyclerDetailedInfo.apply {
                reviews.triggerVisibility(item.reviews.isNotEmpty())
                imageReviews.triggerVisibility(item.reviews.isNotEmpty())
                starLight.triggerVisibility(item.overallRating != 0.0)
                rate.triggerVisibility(item.overallRating != 0.0)
            }
        }

        override fun clickListener(position: Int) {
            binding.itemRecyclerDetailedInfo.savedSticker.setOnClickListener {
                UserManager.triggerSavedPlace(dataList[position].id)
                notifyItemChanged(position)
            }

            binding.itemRecyclerDetailedInfo.itemFrame.setOnClickListener {
                DetailedInfoActivity.intentFor(context, dataList[position].id)
            }
        }
    }
}