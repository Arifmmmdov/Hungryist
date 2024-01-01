package com.example.hungryist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.hungryist.R
import com.example.hungryist.databinding.ItemRecyclerFilterBinding
import com.example.hungryist.generics.ActionListener
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder
import com.example.hungryist.model.SelectStringModel

class SelectedTextRecyclerAdapter(
    val context: Context,
    private var dataList: MutableList<SelectStringModel>,
    private val actionListener: ActionListener<SelectStringModel>
) : BaseRecyclerAdapter<SelectStringModel, ItemRecyclerFilterBinding>(dataList) {
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
            textSelectedAction(item)
            binding.mainFrame.setOnClickListener { onItemSelected(position, item) }
        }

        private fun textSelectedAction(item: SelectStringModel) {
            val backgroundColor =
                if (item.isSelected) R.color.main_color else R.color.card_background
            val textColor = if (item.isSelected) R.color.white else R.color.soft_grey
            binding.title.setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
            binding.title.setTextColor(ContextCompat.getColor(context, textColor))
        }

    }

    private fun onItemSelected(position: Int, item: SelectStringModel) {
        dataList.forEachIndexed { index, selectStringModel ->
            dataList[index] = selectStringModel.copy(isSelected = false)
        }
        dataList[position] = item.copy(isSelected = true)
        notifyDataSetChanged()

        actionListener.run(item)
    }


}