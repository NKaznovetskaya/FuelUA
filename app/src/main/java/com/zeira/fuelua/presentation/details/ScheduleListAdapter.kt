package com.zeira.fuelua.presentation.details

import android.annotation.SuppressLint
import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseListAdapter
import com.zeira.fuelua.core.presentation.BindingHolder
import com.zeira.fuelua.databinding.ScheduleItemBinding
import com.zeira.fuelua.domain.models.WorkSchedule
import com.zeira.fuelua.utils.day


class ScheduleListAdapter(
    onClick: (WorkSchedule) -> Unit
) : BaseListAdapter<WorkSchedule, ScheduleItemBinding>(onClick, ScheduleListDiffCallback) {

    override val layoutId: Int = R.layout.schedule_item


    override fun onBindViewHolder(holder: BindingHolder<ScheduleItemBinding>, position: Int) {
        super.onBindViewHolder(holder, position)

        with(holder.binding){
            val item = getItem(holder.adapterPosition)
            dayLabel.text = item.day(root.context)
            "${item.start} - ${item.end}".also { scheduleLabel.text = it }
        }


    }
}