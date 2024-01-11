package com.example.hungryist.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.hungryist.R
import com.example.hungryist.databinding.ItemRecyclerFilterBinding
import com.example.hungryist.ui.fragment.home.HomeViewModel
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder
import com.example.hungryist.model.SelectStringModel
import javax.inject.Inject

class SelectedTextRecyclerAdapter @Inject constructor(
    val context: Context,
    dataList: MutableList<SelectStringModel>,
    private val viewModel: HomeViewModel,
) : BaseRecyclerAdapter<SelectStringModel, ItemRecyclerFilterBinding>(dataList) {

    private var selectedIndex = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<SelectStringModel, ItemRecyclerFilterBinding> {
        val binding =
            ItemRecyclerFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemRecyclerFilterBinding) :
        BaseViewHolder<SelectStringModel, ItemRecyclerFilterBinding>(binding) {
        override fun bind(item: SelectStringModel) {
            binding.title.text = item.place
            textSelectedAction(item)

        }

        private fun textSelectedAction(item: SelectStringModel) {
            val backgroundColor =
                if (item.isSelected) R.color.main_color else R.color.card_background
            val textColor = if (item.isSelected) R.color.white else R.color.soft_grey
            binding.title.setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
            binding.title.setTextColor(ContextCompat.getColor(context, textColor))
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun clickListener(position: Int) {
            binding.mainFrame.setOnClickListener {
                dataList[selectedIndex].isSelected = false
                dataList[position].isSelected = true
                selectedIndex = position
                notifyDataSetChanged()
                viewModel.onTypeSelected(dataList[position])
            }


        }

    }

}