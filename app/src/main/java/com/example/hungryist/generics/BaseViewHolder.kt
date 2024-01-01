package com.example.hungryist.generics

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<Item, ItemBinding : ViewBinding>(itemBinding: ItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    abstract fun bind(item: Item)

}