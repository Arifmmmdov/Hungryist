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
import com.example.hungryist.ui.fragment.home.HomeViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FilteredInfoRecyclerAdapter @Inject constructor(
    val applicationContext: Context,
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
            binding.run {
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
            binding.savedSticker.setOnClickListener {
                dataList[position].apply {
                    this.saved = !this.saved
                    viewModel.setSavedInfo(id, saved)
                    notifyItemChanged(position)
                }
            }
        }
    }
}