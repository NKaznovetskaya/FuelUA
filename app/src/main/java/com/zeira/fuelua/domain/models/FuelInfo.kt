package com.zeira.fuelua.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FuelInfo(
    val info: String? = "",
    val price: String? = "",
    val type: FuelType? = FuelType.UNKNOWN
) : Parcelable
