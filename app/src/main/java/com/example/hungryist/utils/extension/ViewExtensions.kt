package com.example.hungryist.utils.extension

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hungryist.R
import com.example.hungryist.model.OpenCloseStatusModel
import com.example.hungryist.utils.RestaurantStatusChecker
import com.google.firebase.auth.FirebaseAuth
import kotlin.properties.Delegates


fun View.triggerVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Context.showToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun TextView.setStatus(openCloseTimes: List<OpenCloseStatusModel>?): Int {
    val placeStatus =
        if (openCloseTimes.isNullOrEmpty()) R.string.unknown else if (RestaurantStatusChecker.isRestaurantOpen(
                openCloseTimes
            )
        ) R.string.open_now else R.string.closed_now
    val statusColor =
        if (openCloseTimes.isNullOrEmpty()) R.color.secondary_color else if (RestaurantStatusChecker.isRestaurantOpen(
                openCloseTimes
            )
        ) R.color.main_color else R.color.red

    text = context.getString(placeStatus)
    setTextColor(context.getColor(statusColor))
    return statusColor
}

fun ImageView.setSaved() {
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    triggerVisibility(!uid.isNullOrEmpty())
//                savedSticker.setImageResource(if (item.saved) R.drawable.ic_saved_sticker else R.drawable.ic_unsaved_sticker)
}