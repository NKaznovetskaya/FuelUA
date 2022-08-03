package com.zeira.fuelua.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zeira.fuelua.BuildConfig
import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseFragment
import com.zeira.fuelua.databinding.FragmentMenuBinding
import com.zeira.fuelua.domain.models.MenuItem
import com.zeira.fuelua.domain.models.MenuItemType
import com.zeira.fuelua.presentation.MainMenuActivity
import com.zeira.fuelua.presentation.MainNavFragmentDirections
import com.zeira.fuelua.utils.setDivider
import com.zeira.fuelua.utils.version


class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuBinding
        get() = FragmentMenuBinding::inflate

    private val menuAdapter = MenuAdapter { menuItem ->
        when(menuItem.type) {
            MenuItemType.SETTING -> (requireActivity() as? MainMenuActivity)?.navigate(MainNavFragmentDirections.toSettingsFragment())
            MenuItemType.MANAGE_COMPANY -> (requireActivity() as? MainMenuActivity)?.navigate(MainNavFragmentDirections.toPhoneAuthFragment())
            MenuItemType.ABOUT_US -> (requireActivity() as? MainMenuActivity)?.navigate(MainNavFragmentDirections.toAboutUsFragment())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.menuRv.adapter = menuAdapter
        menuAdapter.submitList(menuItems())
        binding.menuRv.setDivider()

        binding.appVersionTv.text = context?.let { version(it) }
    }

    private fun menuItems() = listOf(MenuItem(MenuItemType.SETTING, R.string.settings),
        MenuItem(MenuItemType.MANAGE_COMPANY, R.string.manage_company),
        MenuItem(MenuItemType.ABOUT_US, R.string.about_us)
    )
}