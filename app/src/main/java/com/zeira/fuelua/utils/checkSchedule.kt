package com.zeira.fuelua.utils

import com.zeira.fuelua.domain.models.WorkSchedule

fun List<WorkSchedule>.checkSchedule(): List<WorkSchedule> {

    val returnableList = mutableSetOf<WorkSchedule>()

    //check weekdays
    for (i in 0..4) {
        if (this[0].start == this[i].start && this[0].end == this[i].end) {
            val value = WorkSchedule(0, this[0].end, this[0].start)
            returnableList.add(value)
        } else {
            returnableList.clear()
            returnableList.addAll(this)
            break
        }
    }
    //check weekends
    if (this[5].start == this[6].start && this[5].end == this[6].end) {
        returnableList.remove(this[5])
        returnableList.remove(this[6])
        val value = WorkSchedule(-1, this[5].end, this[5].start)
        returnableList.add(value)
    }

    return returnableList.toList()


}