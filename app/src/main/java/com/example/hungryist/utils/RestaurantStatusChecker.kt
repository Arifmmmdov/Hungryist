package com.example.hungryist.utils

import androidx.compose.ui.text.toUpperCase
import com.example.hungryist.model.OpenCloseStatusModel
import java.time.LocalDate
import java.time.LocalTime

class RestaurantStatusChecker {

    private val currentDate: LocalDate by lazy {
        LocalDate.now()
    }

    private val currentTime: LocalTime by lazy {
        LocalTime.now()
    }

    fun isRestaurantOpen(times: List<OpenCloseStatusModel>?): Boolean {
        val dayOfWeek = currentDate.dayOfWeek
        val currentDay = times?.find {
            it.day.uppercase() == dayOfWeek.toString()
        }
        val open = LocalTime.parse(currentDay?.start)
        val close = LocalTime.parse(currentDay?.end)

        return currentTime.isAfter(open) && currentTime.isBefore(close)
    }
}