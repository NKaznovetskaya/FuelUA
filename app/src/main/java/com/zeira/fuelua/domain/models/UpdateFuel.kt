package com.zeira.fuelua.domain.models

data class UpdateFuel(
    val id: Int?,
    val availability: Boolean = false,
    val price: Double,
    val quantity_limit: Int,
    val title: String?)
