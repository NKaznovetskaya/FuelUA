package com.zeira.fuelua.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class GasStationModelX(
    val additional_info: String,
    val address: String,
    val company_id: Int,
    val currency_type: String,
    val fuel: @RawValue List<Fuel>,
    val id: Int,
    val latitude: Double,
    val logo: String,
    val city_index:String,
    val longitude: Double,
    val timestamp_create: String,
    val timestamp_update: String,
    var isFavorite: Boolean = false,
    var work_schedule: @RawValue List<WorkSchedule>
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GasStationModelX

        if (additional_info != other.additional_info) return false
        if (address != other.address) return false
        if (company_id != other.company_id) return false
        if (currency_type != other.currency_type) return false
        if (fuel != other.fuel) return false
        if (id != other.id) return false
        if (latitude != other.latitude) return false
        if (logo != other.logo) return false
        if (city_index != other.city_index) return false
        if (longitude != other.longitude) return false
        if (timestamp_create != other.timestamp_create) return false
        if (timestamp_update != other.timestamp_update) return false
        if (isFavorite != other.isFavorite) return false
        if (work_schedule != other.work_schedule) return false

        return true
    }

    override fun hashCode(): Int {
        var result = additional_info.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + company_id
        result = 31 * result + currency_type.hashCode()
        result = 31 * result + fuel.hashCode()
        result = 31 * result + id
        result = 31 * result + latitude.hashCode()
        result = 31 * result + logo.hashCode()
        result = 31 * result + city_index.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + timestamp_create.hashCode()
        result = 31 * result + timestamp_update.hashCode()
        result = 31 * result + isFavorite.hashCode()
        result = 31 * result + work_schedule.hashCode()
        return result
    }
}