package com.zeira.fuelua.core.utils

import android.widget.Toast
import androidx.annotation.StringRes
import com.zeira.fuelua.app.App

class ShowToastManager(private val app: App) : ShowToastInterface {

    private var toast: Toast? = null

    private fun getToast(str: String) = Toast.makeText(app, str, Toast.LENGTH_SHORT)

    override fun showMessage(@StringRes resId: Int) = showMessage(app.applicationContext.getString(resId))

    override fun showMessage(str: String) {
        toast?.cancel()
        toast = getToast(str)
        toast?.show()
    }

    override fun cancelDisplaying() {
        toast?.cancel()
        toast = null
    }
}