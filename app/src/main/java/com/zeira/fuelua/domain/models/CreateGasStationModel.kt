package com.zeira.fuelua.domain.models

import kotlinx.parcelize.RawValue


data class CreateGasStationModel(
    val additional_info: String,
    val address: String,
    val company_id: Int,
    val currency_type: String,
    val fuel: @RawValue List<CreateFuel>,
    val latitude: Double,
    val logo: String,
    val city_index:String,
    val longitude: Double,
    var work_schedule: @RawValue List<WorkSchedule>
)