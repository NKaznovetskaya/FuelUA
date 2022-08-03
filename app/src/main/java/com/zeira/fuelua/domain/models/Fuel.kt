package com.zeira.fuelua.domain.models

data class Fuel(
    val availability: Boolean = false,
  //  val fuel_type: String,
    val id: Int,
    val price: Double,
    val quantity_limit: Int,
    val station: Int,
    val timestamp_create: String,
    val timestamp_update: String,
    val title: String
)