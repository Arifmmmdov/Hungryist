package com.example.hungryist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hungryist.databinding.ItemSimpleTextBinding
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder

class SimpleTextRecyclerAdapter(dataList: List<String>) :
    BaseRecyclerAdapter<String, ItemSimpleTextBinding>(dataList) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding =
            ItemSimpleTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemSimpleTextBinding) :
        BaseViewHolder<String, ItemSimpleTextBinding>(binding) {
        override fun bind(item: String) {
            binding.phone.text = item
        }

    }
}