package com.example.hungryist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.hungryist.R
import com.example.hungryist.databinding.ItemRecyclerFilterBinding
import com.example.hungryist.helper.BaseRecyclerAdapter
import com.example.hungryist.helper.BaseViewHolder
import com.example.hungryist.model.SelectStringModel
import kotlin.coroutines.coroutineContext

class SelectedTextRecyclerAdapter(val context: Context, dataList: List<SelectStringModel>) :
    BaseRecyclerAdapter<SelectStringModel, ItemRecyclerFilterBinding>(dataList) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SelectStringModel, ItemRecyclerFilterBinding> {
        val binding =
            ItemRecyclerFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemRecyclerFilterBinding) :
        BaseViewHolder<SelectStringModel, ItemRecyclerFilterBinding>(binding) {
        override fun bind(item: SelectStringModel) {
            binding.title.text = item.item
            val resource = if (item.isSelected) R.color.main_color else R.color.card_background
            binding.mainFrame.setBackgroundColor(ContextCompat.getColor(context, resource))
        }

    }
}