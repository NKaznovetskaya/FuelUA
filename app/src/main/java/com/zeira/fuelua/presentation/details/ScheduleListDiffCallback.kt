package com.zeira.fuelua.presentation.details

import androidx.recyclerview.widget.DiffUtil
import com.zeira.fuelua.domain.models.GasStationModelX
import com.zeira.fuelua.domain.models.WorkSchedule

val ScheduleListDiffCallback = object : DiffUtil.ItemCallback<WorkSchedule>() {
    override fun areItemsTheSame(oldItem: WorkSchedule, newItem: WorkSchedule): Boolean {
        return oldItem.day == newItem.day
    }

    override fun areContentsTheSame(oldItem: WorkSchedule, newItem: WorkSchedule): Boolean {
        return oldItem == newItem
    }
}