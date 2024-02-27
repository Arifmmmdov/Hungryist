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
import com.example.hungryist.utils.filterutils.FilterableBaseViewModel
import javax.inject.Inject

class SelectedTextRecyclerAdapter(
    val context: Context,
    dataList: MutableList<SelectStringModel>,
    val viewModel: FilterableBaseViewModel,
) : BaseRecyclerAdapter<SelectStringModel, ItemRecyclerFilterBinding>(dataList) {

    private var selectedIndex = dataList.size - 1

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
            binding.title.text = item.name
            textSelectedAction(item)

        }

        private fun textSelectedAction(item: SelectStringModel) {
            val backgroundColor =
                if (item.isSelected) R.color.main_color else R.color.card_background
            val textColor = if (item.isSelected) R.color.white else R.color.soft_grey
            binding.title.setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
            binding.title.setTextColor(ContextCompat.getColor(context, textColor))
        }

        override fun clickListener(position: Int) {
            binding.mainFrame.setOnClickListener {
                if (dataList[position].name == context.getString(R.string.customFilter))
                    removeCustomFilter(position)
                else {

                    if (selectedIndex == position) {
                        dataList[selectedIndex].isSelected = !dataList[selectedIndex].isSelected
                        notifyItemChanged(selectedIndex)
                        selectedIndex = 0
                    } else {
                        dataList[selectedIndex].isSelected = false
                        dataList[position].isSelected = true
                        selectedIndex = position
                        notifyDataSetChanged()
                    }
                    dataList[selectedIndex].takeIf { it.isSelected }?.name?.let { it1 ->
                        viewModel.onTypeSelected(
                            it1
                        )
                    }
                }
            }
        }

        private fun removeCustomFilter(position: Int) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
            viewModel.removeCustomFilter()
        }
    }

    fun addItem(selectStringModel: SelectStringModel) {
        dataList.add(selectStringModel)
        notifyItemInserted(dataList.size - 1)
    }

}