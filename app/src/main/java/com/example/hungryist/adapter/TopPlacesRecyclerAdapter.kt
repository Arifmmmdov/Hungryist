package com.example.hungryist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hungryist.R
import com.example.hungryist.databinding.ItemRecyclerDetailedInfoBinding
import com.example.hungryist.databinding.ItemRecyclerTopPlacesBinding
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder
import com.example.hungryist.model.BaseInfoModel
import com.example.hungryist.model.DetailedInfoModel
import com.example.hungryist.model.SimpleDataModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TopPlacesRecyclerAdapter @Inject constructor(
    @ApplicationContext val applicationContext: Context,
    dataList: List<BaseInfoModel>,
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
                savedSticker.setImageResource(if (item.saved) R.drawable.ic_saved_sticker else R.drawable.ic_unsaved_sticker)
                location.text = item.location
                openStatus.text = item.openStatus
                openEndTime.text = applicationContext.resources.getString(
                    R.string.open_and_close_times,
                    item.openTime,
                    item.closeTime
                )
                rate.text = item.rating.toString()
                reviews.text =
                    applicationContext.resources.getString(R.string.reviews, item.reviews)

                Glide.with(applicationContext).load(item.imageUrl).into(mainImage)
            }
        }

        override fun clickListener(position: Int) {
            binding.itemRecyclerDetailedInfo.savedSticker.setOnClickListener {
                //TODO set item changed to firestore
                dataList[position].saved = !dataList[position].saved
                notifyItemChanged(position)
            }
        }
    }
}