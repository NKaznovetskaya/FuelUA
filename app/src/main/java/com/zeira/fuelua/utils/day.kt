package com.zeira.fuelua.utils

import android.content.Context
import com.zeira.fuelua.R
import com.zeira.fuelua.domain.models.WorkSchedule

fun WorkSchedule.day(context: Context) :String{
    return when(this.day){
        1 ->{
             context.getString(R.string.monday)
        }
        2 ->{
            context.getString(R.string.tuesday)
        }
        3 ->{
            context.getString(R.string.wednesday)
        }
        4 ->{
            context.getString(R.string.thursday)
        }
        5 ->{
            context.getString(R.string.friday)
        }
        6 ->{
            context.getString(R.string.saturday)
        }
        7 ->{
            context.getString(R.string.sunday)
        }
        0 ->{
            context.getString(R.string.weekdays)
        }
        -1 ->{
            context.getString(R.string.weekends)
        }
        else ->{
            context.getString(R.string.unknown)
        }
    }

}