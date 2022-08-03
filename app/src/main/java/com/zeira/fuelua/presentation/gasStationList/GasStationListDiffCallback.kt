package com.zeira.fuelua.presentation.gasStationList

import androidx.recyclerview.widget.DiffUtil
import com.zeira.fuelua.domain.models.GasStationModel
import com.zeira.fuelua.domain.models.GasStationModelX

val GasStationListDiffCallback = object : DiffUtil.ItemCallback<GasStationModelX>(){
    override fun areItemsTheSame(oldItem: GasStationModelX, newItem: GasStationModelX): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GasStationModelX, newItem: GasStationModelX): Boolean {
        return oldItem == newItem
    }


}