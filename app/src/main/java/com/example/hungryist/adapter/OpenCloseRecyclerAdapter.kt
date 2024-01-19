package com.example.hungryist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hungryist.R
import com.example.hungryist.databinding.ItemOpenCloseTimesBinding
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder
import com.example.hungryist.model.OpenCloseStatusModel

class OpenCloseRecyclerAdapter(val context: Context, timesList: List<OpenCloseStatusModel>) :
    BaseRecyclerAdapter<OpenCloseStatusModel, ItemOpenCloseTimesBinding>(timesList) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = ItemOpenCloseTimesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val itemBinding: ItemOpenCloseTimesBinding) :
        BaseViewHolder<OpenCloseStatusModel, ItemOpenCloseTimesBinding>(itemBinding) {
        override fun bind(item: OpenCloseStatusModel) {
            itemBinding.dayName.text = item.day
            itemBinding.openCloseTimes.text =
                context.getString(R.string.open_and_close_times, item.start, item.end)
        }
    }
}