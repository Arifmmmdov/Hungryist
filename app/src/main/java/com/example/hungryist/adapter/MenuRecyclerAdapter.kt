package com.example.hungryist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.hungryist.R
import com.example.hungryist.databinding.ItemMenuBinding
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder
import com.example.hungryist.model.MenuModel

class MenuRecyclerAdapter(val context: Context, dataList: List<MenuModel>) :
    BaseRecyclerAdapter<MenuModel, ItemMenuBinding>(dataList.toMutableList()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<MenuModel, ItemMenuBinding> {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemMenuBinding) :
        BaseViewHolder<MenuModel, ItemMenuBinding>(binding) {

        private val stars by lazy {
            listOf(
                binding.star1,
                binding.star2,
                binding.star3,
                binding.star4,
                binding.star5
            )
        }

        override fun clickListener(position: Int) {
            binding.mainFrame.setOnClickListener {
                //TODO set selected to the Menu Frame
            }
        }

        override fun bind(item: MenuModel) {
            binding.description.text = item.description
            binding.notifyMe.apply {
                setBackgroundColor(context.getColor(if (item.notifyMe) R.color.secondary_color else R.color.grey))
                val textColorResource = if (item.notifyMe) R.color.white else R.color.grey_text
                setTextColor(context.getColor(textColorResource))
            }
            binding.cost.text = context.getString(R.string.cost_in_dollar, item.cost.toString())
            binding.onSale.apply {
                text =
                    context.getString(if (item.onSale) R.string.on_sale else R.string.not_on_sale)
                setTextColor(context.getColor(if (item.onSale) R.color.main_color else R.color.red))
            }
            Glide.with(context).load(item.imageUrl).into(binding.mainImage)
            binding.name.text = item.name
            binding.rating.text = item.rate.toString()
            fillStars(item.rate)
        }

        private fun fillStars(rate: Double) {
            try {
                val numberRate = rate.toInt()
                for (i in stars.indices) {
                    val filledDrawable =
                        if (i < numberRate) R.drawable.ic_filled_star else R.drawable.ic_unfilled_star
                    stars[i].setImageResource(filledDrawable)
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        }

    }
}