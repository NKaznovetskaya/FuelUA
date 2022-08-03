package com.zeira.fuelua.domain.models

import androidx.annotation.StringRes

data class MenuItem(
    val type: MenuItemType,
    @StringRes
    val titleResId: Int
)
