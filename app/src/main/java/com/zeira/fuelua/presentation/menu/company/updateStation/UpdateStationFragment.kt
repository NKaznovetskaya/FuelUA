package com.zeira.fuelua.presentation.menu.company.updateStation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.net.toUri
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseFragment
import com.zeira.fuelua.databinding.FragmentUpdateStationBinding
import com.zeira.fuelua.domain.models.*
import com.zeira.fuelua.presentation.menu.company.createStation.CreateFuelAdapter
import com.zeira.fuelua.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateStationFragment: BaseFragment<FragmentUpdateStationBinding>() {

    private val gasStation by lazy { UpdateStationFragmentArgs.fromBundle(requireArguments()).gasStation }

    private val vm: UpdateGasStationViewModel by viewModel()
    private val createFuelAdapter = CreateFuelAdapter()
    private var companyLogo = ""

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUpdateStationBinding
        get() = FragmentUpdateStationBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.companyLogo()
        vm.companyImageLive.observe(viewLifecycleOwner, companyImageObserver)
        vm.updatedGasStationLive.observe(viewLifecycleOwner, updateGasStationObserver)

        with(binding) {
            createdFuelsRv.adapter = createFuelAdapter

            addressEt.setText(gasStation.address)
            createFuelAdapter.addAll(gasStation.fuel.map { it.toCreateFuel() })

            GasStationUtils.setWorkHours(gasStation.work_schedule,
                Pair(startWorkMonday, endWorkMonday),
                Pair(startWorkTuesday, endWorkTuesday),
                Pair(startWorkWednesday, endWorkWednesday),
                Pair(startWorkThursday, endWorkThursday),
                Pair(startWorkFriday, endWorkFriday),
                Pair(startWorkSaturday, endWorkSaturday),
                Pair(startWorkSunday, endWorkSunday)
            )

            addFuelBtn.setOnClickListener { createFuel() }

            saveBtn.setOnClickListener {
                if (validateCreateStation()) {
                    vm.updateGasStation(gasStation.id, makeUpdateStation())
                }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        fieldsBackgroundListeners()
        setCheckedListener()
        listDataFuelAndCurrency()
    }

    private fun listDataFuelAndCurrency() {
        val fuelTypeArray = ArrayAdapter(
            requireContext(),
            R.layout.view_simple_spinner, FuelType.values().map { it.type }
        )
        fuelTypeArray.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.fuelTypeSp.adapter = fuelTypeArray

        val currencyTypeArray = ArrayAdapter(
            requireContext(),
            R.layout.view_simple_spinner, CurrencyType.values()
        )
        currencyTypeArray.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.currencyTypeSp.adapter = currencyTypeArray

        val regions = Regions.values().map { it.region }.toMutableList()
        regions.removeAt(0)
        val cityTypeArray = ArrayAdapter(
            requireContext(),
            R.layout.view_simple_spinner, regions
        )
        cityTypeArray.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.citySp.adapter = cityTypeArray
    }


    private fun fieldsBackgroundListeners() {
        binding.addressEt.setFieldFocusListener()
        binding.limitEt.setFieldFocusListener()
        binding.priceEt.setFieldFocusListener()
    }


    private fun setCheckedListener() {
        binding.cbWithoutRestrictions.setOnCheckedChangeListener { _, isChecked ->
            binding.limitEt.isFocusableInTouchMode = !isChecked
            if (isChecked && binding.limitEt.isFocused)
                binding.limitEt.clearFocus()
            val fieldBg = if (!isChecked) R.drawable.custom_edit_text_unselected
            else R.drawable.custom_edit_text_not_editable
            binding.limitEt.setBackgroundResource(fieldBg)
        }
    }

    private fun makeUpdateStation(): UpdateGasStationModel {
        with(binding) {
            val currencyType = currencyTypeSp.selectedItem.toString()
            val cityType = citySp.selectedItem.toString()

            return UpdateGasStationModel(address = addressEt.textStr(), company_id = gasStation.company_id,
                currency_type = currencyType, logo = companyLogo,
                fuel = createFuelAdapter.currentList.map { createFuel ->
                    UpdateFuel(gasStation.fuel.find { it.title == createFuel.title }?.id, createFuel.availability,
                    createFuel.price, createFuel.quantity_limit, createFuel.title)
                },
                additional_info = "Графік може змінюватись", latitude = 0.0 , longitude = 0.0,
                work_schedule = listOf(
                    WorkSchedule(1,endWorkMonday.textStr(), startWorkMonday.textStr()),
                    WorkSchedule(2,endWorkTuesday.textStr(), startWorkTuesday.textStr()),
                    WorkSchedule(3,endWorkWednesday.textStr() ,startWorkWednesday.textStr()),
                    WorkSchedule(4,endWorkThursday.textStr(), startWorkThursday.textStr()),
                    WorkSchedule(5,endWorkFriday.textStr(), startWorkFriday.textStr()),
                    WorkSchedule(6,endWorkSaturday.textStr(), startWorkSaturday.textStr()),
                    WorkSchedule(7,endWorkSunday.textStr(), startWorkSunday.textStr())
                ), city_index = Regions.values().find { it.region == cityType }!!.city_index)
        }


    }

    private fun validateCreateStation(): Boolean {
        with(binding) {
            if (addressEt.text.toString().trim().isEmpty()
                || startWorkMonday.text.toString().trim().isEmpty() || startWorkFriday.text.toString().trim().isEmpty()
                || startWorkSaturday.text.toString().trim().isEmpty() || startWorkSunday.text.toString().trim().isEmpty()
                || startWorkThursday.text.toString().trim().isEmpty() || startWorkTuesday.text.toString().trim().isEmpty()
                ||startWorkWednesday.text.toString().trim().isEmpty() || endWorkFriday.text.toString().trim().isEmpty()
                || endWorkMonday.text.toString().trim().isEmpty() || endWorkSaturday.text.toString().trim().isEmpty()
                || endWorkSunday.text.toString().trim().isEmpty() || endWorkThursday.text.toString().trim().isEmpty()
                || endWorkTuesday.text.toString().trim().isEmpty() || endWorkWednesday.text.toString().trim().isEmpty()) {
                helperText.text = getString(R.string.all_fields_must_be_filled)
                binding.helperText.isVisible = true
                CustomKt.forThem(addressEt, startWorkMonday, startWorkFriday, startWorkSaturday, startWorkSunday,
                    startWorkThursday, startWorkTuesday, startWorkWednesday, endWorkFriday, endWorkMonday, endWorkSaturday,
                    endWorkSunday, endWorkThursday, endWorkTuesday, endWorkWednesday) {
                    setBackgroundResource(R.drawable.custom_edit_text_unselected_error)
                }
                return false
            } else {
                binding.helperText.isVisible = false
                return true
            }
        }
    }

    private val companyImageObserver = Observer<String> { urlStr ->
        companyLogo = urlStr
        Glide.with(this)
            .load(urlStr.toUri())
            .centerCrop()
            .placeholder(R.drawable.place_holder)
            .into(binding.stationLogoIv)
    }

    private fun createFuel(){
        binding.addFuelBtn.setOnClickListener {
            val priceStr = binding.priceEt.text.toString()
            val limitStr = binding.limitEt.text.toString()
            val fuelType = FuelType.values().find {  it.type == binding.fuelTypeSp.selectedItem.toString() }
            val price = if (priceStr.isEmpty() || !priceStr.isDigitsOnly()) 0.0 else priceStr.toDouble()
            val limit = if (limitStr.isEmpty() || !limitStr.isDigitsOnly()
                || !binding.cbAvailability.isChecked) 0 else limitStr.toInt()
            val fuel = CreateFuel(binding.cbAvailability.isChecked,
                price, if (binding.cbWithoutRestrictions.isChecked) -1 else limit,
                fuelType?.name?.lowercase())
            createFuelAdapter.addItem(fuel)
            binding.priceEt.text?.clear()
            binding.limitEt.text?.clear()
            binding.cbAvailability.isChecked = false
            binding.cbWithoutRestrictions.isChecked = false
        }
    }

    private val updateGasStationObserver = Observer<UpdateGasStationModel> {
        Log.i("UpdateStationFragment", "station was updated")
        findNavController().popBackStack()
    }

}