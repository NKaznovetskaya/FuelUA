package com.zeira.fuelua.presentation.gasStationList

import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseListAdapter
import com.zeira.fuelua.core.presentation.BindingHolder
import com.zeira.fuelua.databinding.GasStationItemBinding
import com.zeira.fuelua.domain.models.CurrencyType
import com.zeira.fuelua.domain.models.FuelType
import com.zeira.fuelua.domain.models.GasStationModelX
import com.zeira.fuelua.utils.currentDay
import com.zeira.fuelua.utils.str

class GasStationListAdapter(
    onClick: (GasStationModelX) -> Unit,
    val onClickAddToFavorites: (GasStationModelX) -> Unit,
    val onClickRemoveFromFavorites: (id: String?) -> Unit
) :
    BaseListAdapter<GasStationModelX, GasStationItemBinding>(onClick, GasStationListDiffCallback) {

    override val layoutId: Int = R.layout.gas_station_item

    override fun onBindViewHolder(holder: BindingHolder<GasStationItemBinding>, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.binding.item = getItem(holder.adapterPosition)
        val item = getItem(holder.adapterPosition)
        with(holder.binding) {

            if (item.isFavorite) {
                favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_selected)
            } else {
                favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_not_selected)
            }

            favoriteBtn.setOnClickListener {
                if (item.isFavorite) {
                    favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_not_selected)
                    item.isFavorite = false
                    onClickRemoveFromFavorites(item.id.toString())

                } else if (!(item.isFavorite)) {
                    favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_selected)
                    item.isFavorite = true
                    onClickAddToFavorites(item)

                }
            }

            workScheduleTv.text = item.currentDay(root.context)

            // workScheduleTv.text = item.workSchedule.toString()

            val fuelInfo95 =
                item.fuel.find { it.title.lowercase() == FuelType.FUEL_95.name.lowercase() }
            val fuelInfoDiesel =
                item.fuel.find { it.title.lowercase() == FuelType.DIESEL.name.lowercase() }
            val fuelInfoGas =
                item.fuel.find { it.title.lowercase() == FuelType.GAS.name.lowercase() }

            val currencyTypeString = item?.currency_type

            val currencyType = CurrencyType.values()
                .find { it.name.lowercase() == currencyTypeString?.lowercase() } ?: CurrencyType.UAH

                // get currency symbol
            val currencySymbol: String = currencyType.str(root.context)

            // filling in the information about fuel
            petrol95InfoTv.text = fuelInfo95?.str(currencySymbol) ?: root.context.getString(R.string.not_available)
            gasInfoTv.text = fuelInfoGas?.str(currencySymbol) ?: root.context.getString(R.string.not_available)
            dieselInfoTv.text = fuelInfoDiesel?.str(currencySymbol) ?: root.context.getString(R.string.not_available)

        }
    }
}
