package com.zeira.fuelua.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseFragment
import com.zeira.fuelua.databinding.FragmentMainNavBinding

class MainNavFragment: BaseFragment<FragmentMainNavBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainNavBinding
        get() = FragmentMainNavBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navBottomHostFragment = childFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navBottomHostFragment.navController)

    }


}