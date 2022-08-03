package com.zeira.fuelua.presentation.menu.company.createStation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.net.toUri
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseFragment
import com.zeira.fuelua.databinding.FragmentCreateStationBinding
import com.zeira.fuelua.domain.models.*
import com.zeira.fuelua.utils.CustomKt
import com.zeira.fuelua.utils.setFieldFocusListener
import com.zeira.fuelua.utils.textStr
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException

class CreateStationFragment : BaseFragment<FragmentCreateStationBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCreateStationBinding
        get() = FragmentCreateStationBinding::inflate
    private val vm: CreateStationViewModel by viewModel()

    private val createFuelAdapter = CreateFuelAdapter()
    private var company: CompanyModelX? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.gasStationLive.observe(viewLifecycleOwner) {
            Log.i("createStation", "$it")
        }
        vm.companyLive.observe(viewLifecycleOwner) {
            company = it
            try {
                Glide.with(this)
                    .load(it.logo.toUri())
                    .centerCrop()
                    .placeholder(R.drawable.place_holder)
                    .into(binding.stationLogoIv)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        vm.company()

       setCheckedListener()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        listDataFuelAndCurrency()

        binding.saveBtn.setOnClickListener {
            if (validateCreateStation()) {
                val gasStation = makeStation()
                vm.createGasStation(gasStation)
            }
        }

        binding.createdFuelsRv.adapter = createFuelAdapter

        createFuel()

        fieldsBackgroundListeners()
    }

    private fun fieldsBackgroundListeners() {
        binding.addressEt.setFieldFocusListener()
        binding.limitEt.setFieldFocusListener()
        binding.priceEt.setFieldFocusListener()
    }


    private fun makeStation(): CreateGasStationModel {
        with(binding) {
            val currencyType = currencyTypeSp.selectedItem.toString()
            val cityType = citySp.selectedItem.toString()

            return CreateGasStationModel(address = addressEt.textStr(), company_id = company!!.id,
                currency_type = currencyType, logo = company!!.logo,
                fuel = createFuelAdapter.currentList,
                additional_info = getString(R.string.schedule_may_change), latitude = 0.0 , longitude = 0.0,
                city_index = Regions.values().find { it.region == cityType }!!.city_index,
                work_schedule = listOf(WorkSchedule(1,endWorkMonday.textStr(), startWorkMonday.textStr()),
                    WorkSchedule(2,endWorkTuesday.textStr(), startWorkTuesday.textStr()),
                    WorkSchedule(3,endWorkWednesday.textStr() ,startWorkWednesday.textStr()),
                    WorkSchedule(4,endWorkThursday.textStr(), startWorkThursday.textStr()),
                    WorkSchedule(5,endWorkFriday.textStr(), startWorkFriday.textStr()),
                    WorkSchedule(6,endWorkSaturday.textStr(), startWorkSaturday.textStr()),
                    WorkSchedule(7,endWorkSunday.textStr(), startWorkSunday.textStr())
                ))
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

    private fun createFuel() {
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

}
