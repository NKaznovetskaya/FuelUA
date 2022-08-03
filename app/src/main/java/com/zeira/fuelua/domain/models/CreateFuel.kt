package com.zeira.fuelua.domain.models

data class CreateFuel(
    val availability: Boolean = false,
    val price: Double,
    val quantity_limit: Int,
    val title: String?
)
