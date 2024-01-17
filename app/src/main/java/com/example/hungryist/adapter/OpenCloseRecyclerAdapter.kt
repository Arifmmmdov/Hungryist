package com.example.hungryist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hungryist.R
import com.example.hungryist.databinding.ItemOpenCloseTimesBinding
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder
import com.example.hungryist.model.OpenCloseTimes
import com.example.hungryist.model.RatingModel
import com.example.hungryist.utils.extension.triggerVisibility

class OpenCloseRecyclerAdapter(val context: Context, timesList: List<OpenCloseTimes>) :
    BaseRecyclerAdapter<OpenCloseTimes, ItemOpenCloseTimesBinding>(timesList) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = ItemOpenCloseTimesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val itemBinding: ItemOpenCloseTimesBinding) :
        BaseViewHolder<OpenCloseTimes, ItemOpenCloseTimesBinding>(itemBinding) {
        override fun bind(item: OpenCloseTimes) {
            itemBinding.dayName.text = item.day
            itemBinding.openCloseTimes.text =
                context.getString(R.string.open_and_close_times, item.start, item.end)
        }
    }
}