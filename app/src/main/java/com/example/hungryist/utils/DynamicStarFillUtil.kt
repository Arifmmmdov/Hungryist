package com.example.hungryist.utils

import android.view.View
import android.widget.ImageView
import androidx.viewbinding.ViewBinding
import com.example.hungryist.R

class DynamicStarFillUtil(view: View) {

    private val stars by lazy {
        listOf<ImageView>(
            view.findViewById(R.id.star1),
            view.findViewById(R.id.star2),
            view.findViewById(R.id.star3),
            view.findViewById(R.id.star4),
            view.findViewById(R.id.star5)
        )
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