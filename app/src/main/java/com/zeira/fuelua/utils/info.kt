package com.zeira.fuelua.utils

import android.content.Context
import android.location.Geocoder
import com.zeira.fuelua.R
import com.zeira.fuelua.domain.models.CurrencyType



fun CurrencyType?.str(context: Context) : String {
    return when (this) {
        CurrencyType.UAH -> context.resources.getString(R.string.uah)
        CurrencyType.USD -> context.resources.getString(R.string.usd)
        CurrencyType.EUR -> context.resources.getString(R.string.eur)
        else -> context.resources.getString(R.string.uah)
    }
}

fun Context.getAddress(lat: Double, lng: Double): String? {
    val geocoder = Geocoder(this)
    val list = geocoder.getFromLocation(lat,lng,1)
    if (list.size == 0) return null
    return list[0].getAddressLine(0)
}