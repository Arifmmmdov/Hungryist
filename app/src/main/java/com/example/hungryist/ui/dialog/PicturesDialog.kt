package com.example.hungryist.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.example.hungryist.R
import com.example.hungryist.adapter.viewpageradapter.PicturesViewPagerAdapter
import com.example.hungryist.databinding.DialogPicturesBinding
import com.example.hungryist.utils.extension.triggerVisibility

class PicturesDialog(var position: Int, private val mContext: Context, private val images: List<String>) :
    Dialog(mContext) {

    private val binding by lazy {
        DialogPicturesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(binding.root)
        setViews()
        setListeners()
        setCurrentPosition(position)
    }

    private fun setListeners() {
        binding.close.setOnClickListener {
            dismiss()
        }

        binding.imgRight.setOnClickListener {
            setCurrentPosition(position + 1)
        }

        binding.imgLeft.setOnClickListener {
            setCurrentPosition(position - 1)
        }
    }

    private fun setCurrentPosition(currentPosition: Int) {
        position = currentPosition
        binding.viewPager2.currentItem = currentPosition
        binding.imgLeft.triggerVisibility(currentPosition != 0)
        binding.imgRight.triggerVisibility(currentPosition != images.size - 1)
        binding.sequence.text =
            mContext.getString(R.string.sequence, currentPosition + 1, images.size)
    }

    private fun setViews() {
        binding.viewPager2.adapter = PicturesViewPagerAdapter(mContext, images)
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setCurrentPosition(position)
            }
        })
    }
}