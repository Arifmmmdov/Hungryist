package com.example.hungryist.utils.enum

import androidx.recyclerview.widget.RecyclerView
import com.example.hungryist.utils.extension.triggerAnimatedVisibility
import com.example.hungryist.utils.extension.triggerVisibility
import com.facebook.shimmer.ShimmerFrameLayout

enum class VisibleStatusEnum(
    private val shimmerFunction: (ShimmerFrameLayout) -> Unit,
    private val recyclerFunction: (RecyclerView) -> Unit
) {
    VISIBLE(
        shimmerFunction = {
            it.triggerVisibility(false)
        },
        recyclerFunction = {
            it.triggerVisibility(true)
        }
    ),
    SHIMMER(
        shimmerFunction = {
            it.triggerVisibility(true)
        },
        recyclerFunction = {
            it.triggerVisibility(false)
        }
    ),
    INVISIBLE(
        shimmerFunction = {
            it.triggerVisibility(false)
        },
        recyclerFunction = {
            it.triggerVisibility(false)
        }
    );

    fun triggerVisibility(shimmerLayout: ShimmerFrameLayout, recyclerView: RecyclerView) {
        recyclerFunction.invoke(recyclerView)
        shimmerFunction.invoke(shimmerLayout)
    }
}