package com.example.hungryist.generics

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


abstract class BaseRecyclerAdapter<Item, ItemBinding : ViewBinding>(var dataList: MutableList<Item>) :
    RecyclerView.Adapter<BaseViewHolder<Item, ItemBinding>>() {

    abstract override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Item, ItemBinding>

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: BaseViewHolder<Item, ItemBinding>, position: Int) {
        holder.bind(dataList[position])
        holder.clickListener(position)
    }
}