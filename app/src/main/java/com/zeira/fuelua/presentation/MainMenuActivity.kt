package com.zeira.fuelua.presentation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.zeira.fuelua.R
import com.zeira.fuelua.core.presentation.BaseActivity
import com.zeira.fuelua.databinding.ActivityMainMenuBinding



class MainMenuActivity : BaseActivity<ActivityMainMenuBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainMenuBinding
        get() = ActivityMainMenuBinding::inflate


    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = Navigation.findNavController(this, R.id.fragment)

    }


    fun navigate(destination: NavDirections) {
        navController.currentDestination?.getAction(destination.actionId)?.let { action ->
            navController.navigate(action.destinationId, destination.arguments)
        }
    }
}