package com.zeira.fuelua.domain.models

import kotlinx.parcelize.RawValue

data class CreateFuelListModel(val fuel: @RawValue List<CreateFuel>)
