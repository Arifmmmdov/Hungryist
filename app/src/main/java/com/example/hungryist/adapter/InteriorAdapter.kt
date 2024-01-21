package com.example.hungryist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hungryist.R
import com.example.hungryist.databinding.ItemRecyclerInteriorBinding
import com.example.hungryist.ui.dialog.PicturesDialog

class InteriorAdapter(
    val fragmentManager: FragmentManager,
    val context: Context,
    private val fullList: List<String>,
    private val limit: Int,
) : RecyclerView.Adapter<InteriorAdapter.ViewHolder>() {

    private var shortenedList = getShortenedList()
    private var canBeExtended = false

    private fun getShortenedList(): List<String> {
        if (fullList.size > limit)
            canBeExtended = true
        return fullList.take(limit)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = ItemRecyclerInteriorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = shortenedList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        holder.clickListener(position)
    }

    inner class ViewHolder(val binding: ItemRecyclerInteriorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            Glide.with(context)
                .load(shortenedList[position])
                .into(binding.imgInterior)

            if (position == limit - 1 && canBeExtended) {
                binding.countMoreItems.visibility = View.VISIBLE
                binding.countMoreItems.text = context.getString(
                    R.string.count_more_items,
                    fullList.size - shortenedList.size
                )
            } else
                binding.countMoreItems.visibility = View.GONE
        }

        fun clickListener(position: Int) {
            binding.imgInterior.setOnClickListener {
                if (canBeExtended && position == limit - 1) {
                    shortenedList = fullList
                    canBeExtended = false
                    notifyDataSetChanged()
                } else {
                    val dialog = PicturesDialog(position, context, fullList)
                    dialog.show(fragmentManager, "Pictures dialog")
                }
            }
        }
    }
}