package com.zeira.fuelua.presentation.menu.company.companySettings

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zeira.fuelua.core.presentation.BaseViewModel
import com.zeira.fuelua.data.repository.manageCompany.CompanyRepository
import com.zeira.fuelua.domain.models.CompanyModelX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ManageCompanyViewModel(
    private val companyRepository: CompanyRepository
) : BaseViewModel() {

    private var selectedImageFileUriLiveMutable = MutableLiveData<Uri?>()

    private var companyLiveMutable = MutableLiveData<List<CompanyModelX>>()
    var companyLive: LiveData<List<CompanyModelX>> = companyLiveMutable

    // company logo when company is created
    private var companyImageLiveMutable = MutableLiveData<String>()
    var companyImageLive: LiveData<String> = companyImageLiveMutable

    // company logo when company is updated
    private var companyImageUpdateLiveMutable = MutableLiveData<String>()
    var companyImageUpdateLive: LiveData<String> = companyImageUpdateLiveMutable

    private var companyUpdateLiveMutable = MutableLiveData<Unit>()
    var companyUpdateLive: LiveData<Unit> = companyUpdateLiveMutable

    fun uploadCompanyImage() = viewModelScope.launch(Dispatchers.IO) {
        val value = companyRepository.uploadCompanyImage(selectedImageFileUriLiveMutable.value)
        companyImageLiveMutable.postValue(value)

    }

    fun updateCompanyImage() = viewModelScope.launch(Dispatchers.IO) {
        val value = companyRepository.uploadCompanyImage(selectedImageFileUriLiveMutable.value)
        companyImageUpdateLiveMutable.postValue(value)

    }

    fun getSelectedImageUri(selectedImageFileUri: Uri?) {
        selectedImageFileUriLiveMutable.value = selectedImageFileUri
    }

    fun getCompany() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val value = companyRepository.getCompany()
                Log.i("requestResult", " company $value")
                companyLiveMutable.postValue(value)
            } catch (e: Exception) {
                handleErrorX(e)
            }
        }
    }

    fun createCompany(companyModel: CompanyModelX) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                companyRepository.createCompany(companyModel)
            } catch (e: Exception) {
                handleErrorX(e)
            }
        }
    }

    fun updateData(companyModel: CompanyModelX) {
        getCompany()
        val oldCompany = companyLive.value?.get(0)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (oldCompany != null) {
                    companyUpdateLiveMutable.postValue(companyRepository.updateCompanyData(oldCompany, companyModel))
                }
            } catch (e: Exception) {
                handleErrorX(e)
            }
        }

    }


}