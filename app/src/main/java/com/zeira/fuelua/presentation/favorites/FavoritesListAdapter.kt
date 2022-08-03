package com.zeira.fuelua.presentation.favorites

import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseListAdapter
import com.zeira.fuelua.core.presentation.BindingHolder
import com.zeira.fuelua.databinding.GasStationItemBinding
import com.zeira.fuelua.domain.models.CurrencyType
import com.zeira.fuelua.domain.models.FuelType
import com.zeira.fuelua.domain.models.GasStationModelX
import com.zeira.fuelua.utils.currentDay
import com.zeira.fuelua.utils.str

class FavoritesListAdapter(
    onClick: (GasStationModelX) -> Unit,
    var onClickRemoveFromFavorites: (id: String?) -> Unit
) : BaseListAdapter<GasStationModelX, GasStationItemBinding>(onClick, FavoritesListDiffCallback) {

    override val layoutId: Int = R.layout.gas_station_item

    override fun onBindViewHolder(holder: BindingHolder<GasStationItemBinding>, position: Int) {
        super.onBindViewHolder(holder, position)

        with(holder.binding) {
            item = getItem(holder.adapterPosition)
            favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_selected)
            val item = getItem(holder.adapterPosition)

            favoriteBtn.setOnClickListener {
                favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_not_selected)
                item.isFavorite = false
                onClickRemoveFromFavorites(item.id.toString())

            }

            workScheduleTv.text = item.currentDay(root.context)

            val fuelInfo95 = item?.fuel?.find { it.title.lowercase() == FuelType.FUEL_95.name.lowercase() }
            val fuelInfoDiesel = item?.fuel?.find { it.title.lowercase() == FuelType.DIESEL.name.lowercase() }
            val fuelInfoGas = item?.fuel?.find { it.title.lowercase() == FuelType.GAS.name.lowercase() }

            val currencyTypeString = item?.currency_type

            val currencyType = CurrencyType.values()
                .find { it.name.lowercase() == currencyTypeString?.lowercase() } ?: CurrencyType.UAH

            val currencySymbol: String = currencyType.str(root.context)

            // filling in the information about fuel
            petrol95InfoTv.text = fuelInfo95?.str(currencySymbol) ?: root.context.getString(R.string.not_available)
            gasInfoTv.text = fuelInfoGas?.str(currencySymbol) ?: root.context.getString(R.string.not_available)
            dieselInfoTv.text = fuelInfoDiesel?.str(currencySymbol) ?: root.context.getString(R.string.not_available)

        }
    }
}
