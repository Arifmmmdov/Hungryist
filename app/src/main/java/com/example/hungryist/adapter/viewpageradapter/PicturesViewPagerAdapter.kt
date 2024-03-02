package com.example.hungryist.adapter.viewpageradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.hungryist.databinding.ItemDealsOfMonthBinding
import com.example.hungryist.databinding.ItemPagerPicturesBinding
import com.example.hungryist.generics.BaseRecyclerAdapter
import com.example.hungryist.generics.BaseViewHolder

class PicturesViewPagerAdapter(
    private val context: Context,
    images: List<String>,
) :
    BaseRecyclerAdapter<String, ItemPagerPicturesBinding>(images.toMutableList()) {
    private lateinit var binding: ItemPagerPicturesBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<String, ItemPagerPicturesBinding> {
        binding = ItemPagerPicturesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(binding: ItemPagerPicturesBinding) :
        BaseViewHolder<String, ItemPagerPicturesBinding>(binding) {
        override fun bind(item: String) {
            setViews(item)
        }

        private fun setViews(image: String) {
            Glide.with(context).load(image).apply(RequestOptions.centerCropTransform()).into(binding.imageView)
        }
    }


}