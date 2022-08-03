package com.zeira.fuelua.presentation.menu.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zeira.fuelua.BuildConfig
import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseFragment
import com.zeira.fuelua.databinding.FragmentAboutUsBinding
import com.zeira.fuelua.utils.version

class AboutUsFragment: BaseFragment<com.zeira.fuelua.databinding.FragmentAboutUsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAboutUsBinding
        get() = FragmentAboutUsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
           findNavController().popBackStack()
        }


        binding.versionTv.text = context?.let { version(it) }
    }


}