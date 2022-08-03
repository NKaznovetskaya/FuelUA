package com.zeira.fuelua.presentation.gasStationList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zeira.fuelua.core.presentation.BaseViewModel
import com.zeira.fuelua.data.repository.gasStationList.GasStationListRepository
import com.zeira.fuelua.domain.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GasStationListViewModel(
    private val gasStationListRepository: GasStationListRepository
) : BaseViewModel() {

    private var allStation: ArrayList<GasStationModelX> = arrayListOf()


//    private var gasStationListLiveMutable = MutableLiveData<ArrayList<GasStationModel>>()
//    var gasStationListLive: LiveData<ArrayList<GasStationModel>> = gasStationListLiveMutable

    private var exceptionLiveMutable = MutableLiveData<Boolean>()
    var exceptionLive: LiveData<Boolean> = exceptionLiveMutable

    private var gasStationListLiveMutableX = MutableLiveData<List<GasStationModelX>>()
    var gasStationListLiveX: LiveData<List<GasStationModelX>> = gasStationListLiveMutableX


    // get allStation list from Firebase
//    fun getGasStationList() = viewModelScope.launch {
//        gasStationListRepository.getAllGasStation().collect {
//            allStation = it
//            getListWithFavorites()
//        }
//    }


    //compare allGasStation list with favorites and set isFavorite = true
    private suspend fun getListWithFavorites() {
        withContext(Dispatchers.IO) {
            val favoritesList = gasStationListRepository.getFavorites()
            for (item in favoritesList) {
                val model =
                    allStation.find { it.id.toString().contains(item.gasStationId) == true }
                model?.isFavorite = true
            }
            gasStationListLiveMutableX.postValue(allStation)
            Log.i("compareList", favoritesList.toString())
        }
    }

    fun getByFilter(model: FilterModel){
        model.city_index = region(model.city_index)
        model.fuel = fuelType(model.fuel)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val sortedList = gasStationListRepository.getByFilter(model)
                allStation = sortedList
                getListWithFavorites()
            } catch (e: Exception) {
                handleErrorX(e)
                exceptionLiveMutable.postValue(true)
            }
        }

    }

    private fun fuelType(type: String): String {
        val fuelType = FuelType.values().find { it.type == type }

        return fuelType.let { it?.name } ?: ""
    }

    private fun region(cityItem: String): String {
        val regions = Regions.values().find { it.region == cityItem }

        return regions.let { it?.city_index } ?: ""
    }

    //add model to Realm
    fun addToFavorites(data: GasStationModelX) {
        val realmModel = RealmFavoritesModel()

        realmModel.gasStationId = data.id.toString()
        realmModel.logo = data.logo.toString()
        realmModel.latitude = data.latitude
        realmModel.longitude = data.longitude

        viewModelScope.launch(Dispatchers.IO) {
            gasStationListRepository.addToFavorites(realmModel)
        }
    }

    //remove model from Realm
    fun removeFromFavorites(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            gasStationListRepository.removeFromFavorites(id)
        }
    }

    fun getAllGasStation() {
        exceptionLiveMutable.postValue(false)
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val value = gasStationListRepository.getAllStation()

                allStation = value
                getListWithFavorites()

            } catch (e: Exception) {
                handleErrorX(e)
                exceptionLiveMutable.postValue(true)

            }
        }
    }


}