package com.example.hungryist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hungryist.databinding.ItemRecyclerDetailedInfoBinding
import com.example.hungryist.helper.BaseRecyclerAdapter
import com.example.hungryist.helper.BaseViewHolder
import com.example.hungryist.model.SimpleDataModel

class TopPlacesRecyclerAdapter(dataList: List<SimpleDataModel>) :
    BaseRecyclerAdapter<SimpleDataModel, ItemRecyclerDetailedInfoBinding>(dataList) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SimpleDataModel, ItemRecyclerDetailedInfoBinding> {
        val binding = ItemRecyclerDetailedInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemRecyclerDetailedInfoBinding) :
        BaseViewHolder<SimpleDataModel, ItemRecyclerDetailedInfoBinding>(binding) {
        override fun bind(item: SimpleDataModel) {

        }
    }
}