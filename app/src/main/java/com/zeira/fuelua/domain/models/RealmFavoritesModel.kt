package com.zeira.fuelua.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmFavoritesModel(
    @PrimaryKey
    var gasStationId: String = "",
    var logo: String? = "",
    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0,
) : RealmObject()