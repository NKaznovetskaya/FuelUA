package com.zeira.fuelua.presentation.menu.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zeira.fuelua.core.presentation.BaseFragment
import com.zeira.fuelua.databinding.FragmentSettingsBinding

class SettingsFragment: BaseFragment<FragmentSettingsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingsBinding
        get() = FragmentSettingsBinding::inflate


}