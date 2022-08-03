package com.zeira.fuelua.core.utils

import androidx.annotation.StringRes

interface ShowToastInterface {
    fun showMessage(@StringRes resId: Int)
    fun showMessage(str: String)
    fun cancelDisplaying()
}
