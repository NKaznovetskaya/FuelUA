package com.zeira.fuelua.utils

import android.content.Context
import com.zeira.fuelua.R
import com.zeira.fuelua.domain.models.GasStationModelX

fun GasStationModelX.schedule(day: Int, context: Context): String {
    return when (day) {
        1 -> {
            "${this.work_schedule[0].start} - ${this.work_schedule[0].end}"
        }
        2 -> {
            "${this.work_schedule[1].start} - ${this.work_schedule[1].end}"
        }
        3 -> {
            "${this.work_schedule[2].start} - ${this.work_schedule[2].end}"
        }
        4 -> {
            "${this.work_schedule[3].start} - ${this.work_schedule[3].end}"
        }
        5 -> {
            "${this.work_schedule[4].start} - ${this.work_schedule[4].end}"
        }
        6 -> {
            "${this.work_schedule[5].start} - ${this.work_schedule[5].end}"
        }
        7 -> {
            "${this.work_schedule[6].start} - ${this.work_schedule[6].end}"
        }
        else -> {
            context.getString(R.string.unknown)
        }
    }



}