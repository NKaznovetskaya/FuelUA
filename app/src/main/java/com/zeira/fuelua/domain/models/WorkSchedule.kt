package com.zeira.fuelua.domain.models

data class WorkSchedule(
    val day: Int,
    val end: String,
    val start: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WorkSchedule

        if (day != other.day) return false
        if (end != other.end) return false
        if (start != other.start) return false

        return true
    }

    override fun hashCode(): Int {
        var result = day
        result = 31 * result + end.hashCode()
        result = 31 * result + start.hashCode()
        return result
    }
}