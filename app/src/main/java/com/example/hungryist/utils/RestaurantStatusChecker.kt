package com.example.hungryist.utils

import android.content.Context
import androidx.compose.ui.text.toUpperCase
import com.example.hungryist.R
import com.example.hungryist.model.OpenCloseStatusModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object RestaurantStatusChecker {

    private val currentDate: LocalDate by lazy {
        LocalDate.now()
    }

    private val currentTime: LocalTime by lazy {
        LocalTime.now()
    }

    fun isRestaurantOpen(times: List<OpenCloseStatusModel>?,): Boolean {
        return try {
            val dayOfWeek = currentDate.dayOfWeek
            val currentDay = times?.find {
                it.day.uppercase() == dayOfWeek.toString()
            }
            val open = LocalTime.parse(currentDay?.start)
            val close = LocalTime.parse(currentDay?.end)

            currentTime.isAfter(open) && currentTime.isBefore(close)
        } catch (e: Exception) {
            false
        }

    }

    fun getTimesToday(context: Context, times: List<OpenCloseStatusModel>?): String {
        try {
            val dayOfWeek = currentDate.dayOfWeek
            val currentDay = times?.find {
                it.day.uppercase() == dayOfWeek.toString()
            }
            val open = LocalTime.parse(currentDay?.start)
            val close = LocalTime.parse(currentDay?.end)

            val formatter = DateTimeFormatter.ofPattern("h a")

            return context.getString(
                R.string.open_and_close_times,
                open.format(formatter),
                close.format(formatter)
            )
        } catch (e: Exception) {
            return ""
        }

    }
}