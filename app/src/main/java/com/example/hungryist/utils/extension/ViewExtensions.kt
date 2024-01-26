package com.example.hungryist.utils.extension

import android.content.Context
import android.view.View
import android.widget.Toast


fun View.triggerVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Context.showToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}