package com.example.hungryist.utils.extension

import android.view.View


fun View.triggerVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}