package com.example.hungryist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hungryist.databinding.ItemSelectMapPlaceBinding
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder
import com.google.android.libraries.places.api.model.AutocompletePrediction

class SelectMapPlaceAdapter(
    predictions: MutableList<AutocompletePrediction>,
    val callback: (AutocompletePrediction) -> Unit,
) :
    BaseRecyclerAdapter<AutocompletePrediction, ItemSelectMapPlaceBinding>(predictions) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<AutocompletePrediction, ItemSelectMapPlaceBinding> {
        val binding =
            ItemSelectMapPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MViewHolder(binding)
    }

    fun update(predictions: MutableList<AutocompletePrediction>) {
        dataList = predictions
        notifyDataSetChanged()
    }

    inner class MViewHolder(val binding: ItemSelectMapPlaceBinding) :
        BaseViewHolder<AutocompletePrediction, ItemSelectMapPlaceBinding>(binding) {
        override fun bind(item: AutocompletePrediction) {
            binding.mainText.text = item.getPrimaryText(null)
            binding.description.text = item.getSecondaryText(null)
        }

        override fun clickListener(position: Int) {
            binding.mainFrame.setOnClickListener {
                callback(dataList[position])
            }
        }
    }
}