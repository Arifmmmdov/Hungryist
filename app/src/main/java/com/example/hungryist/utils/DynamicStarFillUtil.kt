package com.example.hungryist.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
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

    private val txtRating:TextView by lazy {
        view.findViewById(R.id.rating)
    }

    fun fillStars(rate: Double,isText:Boolean) {
        try {
            val numberRate = rate.toInt()
            for (i in stars.indices) {
                val filledDrawable =
                    if (i < numberRate) R.drawable.ic_filled_star else R.drawable.ic_unfilled_star
                stars[i].setImageResource(filledDrawable)
            }
            if(isText) txtRating.text = rate.toString()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }
}