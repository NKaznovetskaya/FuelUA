package com.zeira.fuelua.presentation.favorites

import androidx.recyclerview.widget.DiffUtil
import com.zeira.fuelua.domain.models.GasStationModel
import com.zeira.fuelua.domain.models.GasStationModelX

val FavoritesListDiffCallback = object : DiffUtil.ItemCallback<GasStationModelX>() {
    override fun areItemsTheSame(oldItem: GasStationModelX, newItem: GasStationModelX): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GasStationModelX, newItem: GasStationModelX): Boolean {
        return oldItem == newItem
    }
}