package com.zeira.fuelua.utils

import android.content.Context
import com.zeira.fuelua.BuildConfig
import com.zeira.fuelua.R

fun version(context: Context) : String {
    val appName = context.resources.getString(R.string.app_name)
    val version = BuildConfig.VERSION_NAME
    return String.format("%s %s", appName, version)
}