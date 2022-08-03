package com.zeira.fuelua.utils

import android.widget.EditText
import com.zeira.fuelua.domain.models.WorkSchedule

object GasStationUtils {

    fun setWorkHours(workSchedules: List<WorkSchedule>, vararg editTextPair: Pair<EditText, EditText>) {
        editTextPair.forEachIndexed { index, item ->
            val workSchedule = workSchedules.find { (index + 1) == it.day }
            workSchedule?.let { ws ->
                item.first.setText(ws.start)
                item.second.setText(ws.end)
            }
        }
    }

}