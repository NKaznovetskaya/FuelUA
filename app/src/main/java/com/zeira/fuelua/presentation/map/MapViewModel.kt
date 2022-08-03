package com.zeira.fuelua.presentation.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zeira.fuelua.core.presentation.BaseViewModel
import com.zeira.fuelua.data.repository.map.MapRepository
import com.zeira.fuelua.domain.models.GasStationModel
import com.zeira.fuelua.domain.models.GasStationModelX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MapViewModel(
    private val mapRepository: MapRepository
) : BaseViewModel() {

    private var gasStationListLiveMutable = MutableLiveData<ArrayList<GasStationModelX>>()
    var gasStationListLive: LiveData<ArrayList<GasStationModelX>> = gasStationListLiveMutable

    private var exceptionLiveMutable = MutableLiveData<Boolean>()
    var exceptionLive: LiveData<Boolean> = exceptionLiveMutable

    // get all gasStation list from Firebase
    fun getAllGasStation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val value = mapRepository.getAllStation()
                exceptionLiveMutable.postValue(false)
                Log.i("requestResult", value.toString())
                gasStationListLiveMutable.postValue(value)
            } catch (e: Exception) {
                handleErrorX(e)
                exceptionLiveMutable.postValue(true)
            }
        }
    }
}