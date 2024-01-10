package com.example.hungryist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hungryist.databinding.ItemDealsOfMonthBinding
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder

class DealsOfMonthAdapter(
    val context: Context,
    dataList: List<String>,
) :
    BaseRecyclerAdapter<String, ItemDealsOfMonthBinding>(dataList) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<String, ItemDealsOfMonthBinding> {
        val binding = ItemDealsOfMonthBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemDealsOfMonthBinding) :
        BaseViewHolder<String, ItemDealsOfMonthBinding>(binding) {
        override fun bind(item: String) {
            Glide.with(context)
                .load(item)
                .into(binding.imageView)
        }

        override fun clickListener(position: Int) {
            //TODO setClickable to images for seeing detailed information about deals of month in future!
        }
    }
}