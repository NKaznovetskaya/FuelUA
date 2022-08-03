package com.zeira.fuelua.presentation.menu.company.listStation

import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseListAdapter
import com.zeira.fuelua.core.presentation.BindingHolder
import com.zeira.fuelua.databinding.GasStationItemForCompanyBinding
import com.zeira.fuelua.domain.models.CurrencyType
import com.zeira.fuelua.domain.models.FuelType
import com.zeira.fuelua.domain.models.GasStationModelX
import com.zeira.fuelua.presentation.gasStationList.GasStationListDiffCallback
import com.zeira.fuelua.utils.currentDay
import com.zeira.fuelua.utils.str

class CompanyGasStationListAdapter(
    onClick: (GasStationModelX) -> Unit,
    val onClickEditStation: (GasStationModelX) -> Unit,
    val onClickRemoveStation: (Int) -> Unit
): BaseListAdapter<GasStationModelX, GasStationItemForCompanyBinding>(onClick, GasStationListDiffCallback) {

    override val layoutId: Int
        get() = R.layout.gas_station_item_for_company

    override fun onBindViewHolder(holder: BindingHolder<GasStationItemForCompanyBinding>, position: Int){
       super.onBindViewHolder(holder, position)

        with(holder.binding) {
            val station = getItem(holder.adapterPosition)
            item = station

            root.setOnClickListener { onClick?.let { it1 -> it1(station) } }
            editBtn.setOnClickListener { onClickEditStation(station) }
            deleteBtn.setOnClickListener { onClickRemoveStation(station.id) }

            workScheduleTv.text = station.currentDay(root.context)

            val fuelInfo95 =
                station.fuel.find { it.title.lowercase() == FuelType.FUEL_95.name.lowercase() }
            val fuelInfoDiesel =
                station.fuel.find { it.title.lowercase() == FuelType.DIESEL.name.lowercase() }
            val fuelInfoGas =
                station.fuel.find { it.title.lowercase() == FuelType.GAS.name.lowercase() }

            val currencyTypeString = station?.currency_type

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