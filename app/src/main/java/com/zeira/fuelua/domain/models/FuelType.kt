package com.zeira.fuelua.domain.models

enum class FuelType(val type: String) {
    FUEL_95("А95"),
    FUEL_92("А92"),
    DIESEL("ДП"),
    GAS("Газ"),
    FUEL_95_PULLS("А95 Pulls"),
    DIESEL_PULLS("ДП Pulls"),
    DIESEL_MUSTANG("ДП Mustang"),
    FUEL_95_MUSTANG("А95 Mustang"),
    UNKNOWN("Невідомо")
}