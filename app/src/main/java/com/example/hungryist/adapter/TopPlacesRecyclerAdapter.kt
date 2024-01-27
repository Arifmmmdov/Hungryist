package com.example.hungryist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hungryist.R
import com.example.hungryist.databinding.ItemRecyclerTopPlacesBinding
import com.example.hungryist.ui.fragment.home.HomeViewModel
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.utils.RestaurantStatusChecker
import com.example.hungryist.utils.extension.setStatus
import com.example.hungryist.utils.extension.setSaved
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
            binding.title.text = item.titleName
            binding.itemRecyclerDetailedInfo.run {
                name.text = item.name
                openStatus.setStatus(item.openCloseTimes)
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

        override fun clickListener(position: Int) {
            binding.itemRecyclerDetailedInfo.savedSticker.setOnClickListener {
                dataList[position].apply {
//                    this.saved = !this.saved
//                    viewModel.setSavedInfo(id, referenceId, saved)
                    notifyItemChanged(position)
                }
            }

            binding.itemRecyclerDetailedInfo.itemFrame.setOnClickListener {
//                DetailedInfoActivity.intentFor(context, dataList[position].referenceId)
            }
        }
    }
}