package com.zeira.fuelua.presentation.menu.company.createStation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zeira.fuelua.core.presentation.BaseViewModel
import com.zeira.fuelua.data.repository.gasStationList.GasStationListRepository
import com.zeira.fuelua.data.repository.manageCompany.CompanyRepository
import com.zeira.fuelua.domain.models.CompanyModelX
import com.zeira.fuelua.domain.models.CreateGasStationModel
import com.zeira.fuelua.domain.models.GasStationModelX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateStationViewModel(
    private val gasStationListRepository: GasStationListRepository,
    private val companyRepository: CompanyRepository
    ): BaseViewModel() {

    private var gasStationLiveMutable = MutableLiveData<CreateGasStationModel>()
    var gasStationLive: LiveData<CreateGasStationModel> = gasStationLiveMutable

    private var companyLiveMutable = MutableLiveData<CompanyModelX>()
    var companyLive: LiveData<CompanyModelX> = companyLiveMutable


    fun createGasStation(createGasStationModel: CreateGasStationModel){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val gasStation = gasStationListRepository.createGasStation(createGasStationModel)
                gasStationLiveMutable.postValue(gasStation)
            } catch (e: Exception){
                handleErrorX(e)
            }
        }

    }

    fun company() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val companyList = companyRepository.getCompany()
                if (companyList.isNotEmpty())
                    companyLiveMutable.postValue(companyList.first())
            } catch (e: Exception){
                handleErrorX(e)
            }
        }
    }

}