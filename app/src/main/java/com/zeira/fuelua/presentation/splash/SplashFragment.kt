package com.zeira.fuelua.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zeira.fuelua.core.presentation.BaseFragment
import com.zeira.fuelua.databinding.FragmentSplashBinding
import com.zeira.fuelua.presentation.MainMenuActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate

    private val vm: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.subscribeToTopicSuccessLive.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(context, MainMenuActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
