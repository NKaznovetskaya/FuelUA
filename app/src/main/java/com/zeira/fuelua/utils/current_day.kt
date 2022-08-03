package com.zeira.fuelua.utils

import android.content.Context
import com.zeira.fuelua.R
import com.zeira.fuelua.domain.models.GasStationModelX
import java.util.*

fun GasStationModelX.currentDay(context: Context): String {

    val calendar: Calendar = Calendar.getInstance()

    return when (calendar.get(Calendar.DAY_OF_WEEK)) {

        Calendar.MONDAY -> {
            this.schedule(1, context)
        }
        Calendar.TUESDAY -> {
            this.schedule(2, context)
        }
        Calendar.WEDNESDAY -> {
            this.schedule(3, context)
        }
        Calendar.THURSDAY -> {
            this.schedule(4, context)
        }
        Calendar.FRIDAY -> {
            this.schedule(5, context)
        }
        Calendar.SATURDAY -> {
            this.schedule(6, context)
        }
        Calendar.SUNDAY -> {
            this.schedule(7, context)
        }
        else -> {
            context.getString(R.string.unknown)
        }
    }

}