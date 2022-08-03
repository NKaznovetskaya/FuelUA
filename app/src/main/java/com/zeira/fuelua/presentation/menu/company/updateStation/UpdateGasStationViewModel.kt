package com.zeira.fuelua.presentation.menu.company.updateStation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zeira.fuelua.core.presentation.BaseViewModel
import com.zeira.fuelua.data.repository.gasStationList.GasStationListRepository
import com.zeira.fuelua.data.repository.manageCompany.CompanyRepository
import com.zeira.fuelua.domain.models.CompanyModelX
import com.zeira.fuelua.domain.models.CreateGasStationModel
import com.zeira.fuelua.domain.models.GasStationModelX
import com.zeira.fuelua.domain.models.UpdateGasStationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateGasStationViewModel(
    private val gasStationListRepository: GasStationListRepository,
    private val companyRepository: CompanyRepository
) : BaseViewModel() {

    private val updatedGasStationLiveMutable = MutableLiveData<UpdateGasStationModel>()
    val updatedGasStationLive: LiveData<UpdateGasStationModel> = updatedGasStationLiveMutable

    private var companyImageLiveMutable = MutableLiveData<String>()
    var companyImageLive: LiveData<String> = companyImageLiveMutable

    fun updateGasStation(id: Int, updateGasStationModel: UpdateGasStationModel){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val gasStation = gasStationListRepository.updateGasStation(id, updateGasStationModel)
                updatedGasStationLiveMutable.postValue(gasStation)
            }catch (e: Exception) {
                handleErrorX(e)
            }
        }
    }


    fun companyLogo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val companyList = companyRepository.getCompany()
                if (companyList.isNotEmpty()) {
                    companyImageLiveMutable.postValue(companyList.first().logo)
                }
            } catch (e: Exception){
                handleErrorX(e)
            }
        }
    }
}