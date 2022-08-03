package com.zeira.fuelua.presentation.menu.company.createStation

import androidx.recyclerview.widget.DiffUtil
import com.zeira.fuelua.domain.models.CreateFuel

class CreateFuelDiffCallback: DiffUtil.ItemCallback<CreateFuel>() {

    override fun areItemsTheSame(oldItem: CreateFuel, newItem: CreateFuel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CreateFuel, newItem: CreateFuel): Boolean {
        return oldItem.availability == newItem.availability
                && oldItem.price == newItem.price
                && oldItem.quantity_limit == newItem.quantity_limit
                && oldItem.title == newItem.title

    }


}