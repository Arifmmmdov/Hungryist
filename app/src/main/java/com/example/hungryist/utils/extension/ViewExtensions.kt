package com.example.hungryist.utils.extension

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hungryist.R
import com.example.hungryist.model.OpenCloseStatusModel
import com.example.hungryist.utils.RestaurantStatusChecker
import com.example.hungryist.utils.UserManager


fun View.triggerVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.triggerAnimatedVisibility(isVisible: Boolean) {
    if (isVisible) animateShowView() else animateHideView()
}

fun View.animateShowView() {
    this.visibility = View.VISIBLE
    this.alpha = 0f
    this.animate()
        .alpha(1f)
        .start();
}

fun View.animateHideView() {
    this.animate()
        .alpha(0f)
        .withEndAction {
            this.visibility = View.GONE
        }
        .start()
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

fun ImageView.setSaved(placeId: String, userManager: UserManager) {
    val uid = userManager.getUserId()
    triggerVisibility(!uid.isNullOrEmpty())
    setImageResource(if (userManager.checkSaved(placeId)) R.drawable.ic_saved_sticker else R.drawable.ic_unsaved_sticker)
}