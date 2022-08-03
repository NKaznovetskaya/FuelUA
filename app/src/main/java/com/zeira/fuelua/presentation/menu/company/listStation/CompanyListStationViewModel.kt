package com.zeira.fuelua.presentation.menu.company.listStation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zeira.fuelua.core.presentation.BaseViewModel
import com.zeira.fuelua.data.repository.gasStationList.GasStationListRepository
import com.zeira.fuelua.domain.models.GasStationModelX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompanyListStationViewModel(private val gasStationListRepository: GasStationListRepository) :
    BaseViewModel() {

    private val allGasStationMutableList = MutableLiveData<List<GasStationModelX>>()
    var allGasStationLive: LiveData<List<GasStationModelX>> = allGasStationMutableList

    fun getAllStation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val allStation = gasStationListRepository.getAllStation()
                allGasStationMutableList.postValue(allStation)
            } catch (e: Exception) {
                handleErrorX(e)
            }
        }
    }

    fun removeGasStation(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                gasStationListRepository.removeGasStation(id)
                val allStation = gasStationListRepository.getAllStation()
                allGasStationMutableList.postValue(allStation)
            } catch (e: Exception) {
                handleErrorX(e)
            }
        }
    }

}