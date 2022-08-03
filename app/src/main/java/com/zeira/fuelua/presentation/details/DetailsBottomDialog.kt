package com.zeira.fuelua.presentation.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseBottomSheetFragment
import com.zeira.fuelua.databinding.FragmentDetailsBinding
import com.zeira.fuelua.domain.models.*
import com.zeira.fuelua.utils.Constants
import com.zeira.fuelua.utils.checkSchedule
import com.zeira.fuelua.utils.schedule
import com.zeira.fuelua.utils.str
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsBottomDialog : BaseBottomSheetFragment<FragmentDetailsBinding>() {

    companion object {
        const val DETAILS_BOTTOM_DIALOG_TAG = "DetailsBottomDialog"
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding
        get() = FragmentDetailsBinding::inflate

    private val vm: DetailsViewModel by viewModel()

    private var adapter = ScheduleListAdapter(onClick = {})

    private var favList: ArrayList<RealmFavoritesModel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        vm.getFavorites()

        return view

    }

    override fun onStart() {
        super.onStart()
        binding.scheduleRv.layoutManager = LinearLayoutManager(context)
        binding.scheduleRv.adapter = adapter
        val args = arguments
        val data = args?.getParcelable(Constants.STATION_DETAILS) as GasStationModelX?
        val logo = data?.logo.toString()
        val fuelInfo95 =
            data?.fuel?.find { it.title.lowercase() == FuelType.FUEL_95.name.lowercase() }
        val fuelInfo92 =
            data?.fuel?.find { it.title.lowercase() == FuelType.FUEL_92.name.lowercase() }
        val fuelInfoDiesel =
            data?.fuel?.find { it.title.lowercase() == FuelType.DIESEL.name.lowercase() }
        val fuelInfoGas = data?.fuel?.find { it.title.lowercase() == FuelType.GAS.name.lowercase() }

        val schedule = data?.work_schedule?.checkSchedule()

        adapter.submitList(schedule)

        val currencyTypeString = data?.currency_type

        val currencyType = CurrencyType.values()
            .find { it.name.lowercase() == currencyTypeString?.lowercase() } ?: CurrencyType.UAH

        val currencySymbol: String? = context?.let { currencyType.str(it) }


        vm.favoritesLive.observe(viewLifecycleOwner) {
            favList = it
            for (i in it.indices) {
                Log.i("tag", it[i].gasStationId)
            }
            checkFavorites(data?.id.toString())
        }
        with(binding) {

            // filling in the information about fuel
            petrol95InfoTv.text =
                currencySymbol?.let { fuelInfo95?.str(it) } ?: getString(R.string.not_available)
            petrol92InfoTv.text =
                currencySymbol?.let { fuelInfo92?.str(it) } ?: getString(R.string.not_available)
            gasInfoTv.text =
                currencySymbol?.let { fuelInfoGas?.str(it) } ?: getString(R.string.not_available)
            dieselInfoTv.text =
                currencySymbol?.let { fuelInfoDiesel?.str(it) } ?: getString(R.string.not_available)

            binding.addressTv.text = data?.address.toString()
            binding.additionalInfoTv.text = data?.additional_info.toString()

        }
        Glide
            .with(this)
            .load(logo)
            .centerCrop()
            .placeholder(R.color.white)
            .into(binding.gasStationLogoIv)

        binding.favoriteBtn.setOnClickListener {

            if (data?.isFavorite == true) {
                binding.favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_not_selected)
                data.isFavorite = false
                vm.removeFromFavorites(data.id.toString())
            } else if (data?.isFavorite == false) {
                binding.favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_selected)
                data.isFavorite = true
                vm.addToFavorites(data)
            }

        }

    }

    private fun checkFavorites(gasStationId: String?) {
        for (i in favList.indices) {
            if (favList[i].gasStationId == gasStationId) {
                binding.favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_selected)
            }
        }
    }

}