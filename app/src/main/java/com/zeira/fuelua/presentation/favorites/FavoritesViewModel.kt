package com.zeira.fuelua.presentation.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zeira.fuelua.core.presentation.BaseViewModel
import com.zeira.fuelua.data.repository.favorites.FavoritesRepository
import com.zeira.fuelua.domain.models.GasStationModel
import com.zeira.fuelua.domain.models.GasStationModelX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository
) : BaseViewModel() {

    private var allStation: Set<GasStationModelX> = hashSetOf()

    private var exceptionLiveMutable = MutableLiveData<Boolean>()
    var exceptionLive: LiveData<Boolean> = exceptionLiveMutable

    private var favoritesLiveMutable = MutableLiveData<ArrayList<GasStationModelX>>()
    val favoritesLive: LiveData<ArrayList<GasStationModelX>> = favoritesLiveMutable

    // get all gasStation list from Firebase
    fun getFavorites()  {
        //get all station for compare with favorites
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val value = favoritesRepository.getAllStation()
                exceptionLiveMutable.postValue(false)
                allStation = value.toSet()
                compareWithFavorites()
            }catch (e: Exception){
                 handleErrorX(e)
                exceptionLiveMutable.postValue(true)
            }
        }

    }

    //compare favorites and all station list and return list of favorites as GasStationModel, if it`s possible
    private fun compareWithFavorites() {

        viewModelScope.launch(Dispatchers.IO) {
            val favoritesList = favoritesRepository.getFavorites()

            val list: ArrayList<GasStationModelX> = arrayListOf()
            for (i in favoritesList.indices) {
                val model =
                    allStation.find { it.id.toString() == favoritesList[i].gasStationId }
                Log.i("logg", model.toString())
                model?.let { list.add(it) }
            }
            favoritesLiveMutable.postValue(list)
        }
    }

    // remove model from Realm
    fun removeFromFavorites(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesRepository.removeFromFavorites(id)
        }
    }

}