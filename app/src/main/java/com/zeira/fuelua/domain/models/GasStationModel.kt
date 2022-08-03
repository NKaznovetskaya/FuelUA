package com.zeira.fuelua.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GasStationModel(
    var gasStationId: String? = "",
    var companyId: String? = "",
    var logo: String? = "",
    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0,
    var address: String? = "",
    var additionalInfo: String? = "",
    var workSchedule: String? = "",
    var currencyType: CurrencyType? = CurrencyType.UAH,
    var fuelInfo: List<FuelInfo>? = listOf(),
    var isFavorite: Boolean = false,
    var createdAt: Long? = 0,
    var updatedAt: Long? = 0
) : Parcelable
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GasStationModel

        if (gasStationId != other.gasStationId) return false
        if (companyId != other.companyId) return false
        if (logo != other.logo) return false
        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (address != other.address) return false
        if (additionalInfo != other.additionalInfo) return false
        if (workSchedule != other.workSchedule) return false
        if (currencyType != other.currencyType) return false
        if (fuelInfo != other.fuelInfo) return false
        if (isFavorite != other.isFavorite) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = gasStationId?.hashCode() ?: 0
        result = 31 * result + (companyId?.hashCode() ?: 0)
        result = 31 * result + (logo?.hashCode() ?: 0)
        result = 31 * result + (latitude?.hashCode() ?: 0)
        result = 31 * result + (longitude?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (additionalInfo?.hashCode() ?: 0)
        result = 31 * result + (workSchedule?.hashCode() ?: 0)
        result = 31 * result + (currencyType?.hashCode() ?: 0)
        result = 31 * result + (fuelInfo?.hashCode() ?: 0)
        result = 31 * result + isFavorite.hashCode()
        result = 31 * result + (createdAt?.hashCode() ?: 0)
        result = 31 * result + (updatedAt?.hashCode() ?: 0)
        return result
    }
}

