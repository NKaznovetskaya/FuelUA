package com.zeira.fuelua.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zeira.fuelua.core.presentation.BaseViewModel
import com.zeira.fuelua.data.repository.bottomSheet.DetailsRepository
import com.zeira.fuelua.domain.models.GasStationModel
import com.zeira.fuelua.domain.models.GasStationModelX
import com.zeira.fuelua.domain.models.RealmFavoritesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val detailsRepository: DetailsRepository
) : BaseViewModel() {

    private var favoritesLiveMutable = MutableLiveData<ArrayList<RealmFavoritesModel>>()
    val favoritesLive: LiveData<ArrayList<RealmFavoritesModel>> = favoritesLiveMutable

    // remove model from Realm
    fun removeFromFavorites(gasStationId: String?) = gasStationId?.let {
        viewModelScope.launch(Dispatchers.IO) {
            detailsRepository.removeFromFavorites(
                it
            )
        }
    }

    // add model to Realm
    fun addToFavorites(data: GasStationModelX) {
        val realmModel = RealmFavoritesModel()
        realmModel.gasStationId = data.id.toString()
        realmModel.logo = data.logo.toString()
        realmModel.latitude = data.latitude
        realmModel.longitude = data.longitude
        viewModelScope.launch(Dispatchers.IO) {
            detailsRepository.addToFavorites(realmModel)
        }

    }

    // get Favorites list from realm
    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val favorites = detailsRepository.getFavorites()
            favoritesLiveMutable.postValue(favorites)
        }
    }

}