package com.zeira.fuelua.domain.models

data class FilterModel(
    var city_index: String,
    val availability: Boolean,
    var fuel: String
) {
    fun toQueryMap(): Map<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()

        if (this.city_index.isNotEmpty()) {
            map["city_index"] = city_index
        }
        if (this.availability) {
            map["availability"] = availability.toString()
        }
        if (this.fuel.isNotEmpty() && this.fuel != FuelType.UNKNOWN.name) {
            map["fuel"] = fuel
        }

        return map.toMap()
    }

}