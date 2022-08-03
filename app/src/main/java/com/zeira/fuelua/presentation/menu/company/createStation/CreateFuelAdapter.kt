package com.zeira.fuelua.presentation.menu.company.createStation

import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseListAdapter
import com.zeira.fuelua.core.presentation.BindingHolder
import com.zeira.fuelua.databinding.ItemCreateFuelBinding
import com.zeira.fuelua.domain.models.CreateFuel
import com.zeira.fuelua.domain.models.FuelType
import com.zeira.fuelua.utils.str

class CreateFuelAdapter:
    BaseListAdapter<CreateFuel, ItemCreateFuelBinding>(diffCallback = CreateFuelDiffCallback()) {

    override val layoutId: Int
        get() = R.layout.item_create_fuel

    private val items = mutableListOf<CreateFuel>()

    override fun onBindViewHolder(holder: BindingHolder<ItemCreateFuelBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.binding.typeTv.text = FuelType.values().find { it.name.lowercase() == item.title }?.type
        holder.binding.priceTv.text = item.price.toString()
        holder.binding.limitTv.text = item.str()
    }

    fun addItem(createFuel: CreateFuel) {
        items.find { it.title == createFuel.title }?.let { items.remove(it) }
        items.add(createFuel)
        submitList(items)
        notifyDataSetChanged()
    }

    fun addAll(createFuels: List<CreateFuel>) {
        createFuels.forEach { addItem(it) }
    }

}