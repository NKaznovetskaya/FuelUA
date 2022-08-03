package com.zeira.fuelua.presentation

import android.os.Bundle
import android.view.LayoutInflater
import com.zeira.fuelua.core.presentation.BaseActivity
import com.zeira.fuelua.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}