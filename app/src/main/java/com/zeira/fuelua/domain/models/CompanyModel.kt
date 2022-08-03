package com.zeira.fuelua.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompanyModel(
    var companyId: String? = "",
    var companyName: String? = "",
    var companyPhoneNumber: String? = "",
    var logo: String? = "",
    var description: String? = ""
) : Parcelable