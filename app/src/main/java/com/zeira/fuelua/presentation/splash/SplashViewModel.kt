package com.zeira.fuelua.presentation.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zeira.fuelua.core.presentation.BaseViewModel
import com.zeira.fuelua.data.repository.splash.SplashRepository
import com.zeira.fuelua.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel(private val splashRepository: SplashRepository) : BaseViewModel() {

    init {
        subscribeToTopic()
    }

    private var subscribeToTopicSuccessLiveMutable = MutableLiveData<Boolean>()
    val subscribeToTopicSuccessLive: LiveData<Boolean> = subscribeToTopicSuccessLiveMutable

    fun subscribeToTopic() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                splashRepository.subscribeToTopic(Constants.STATION_UPDATE)
            } catch (e: Exception) {
                Log.e("exception", "station_update $e")
                try {
                    splashRepository.subscribeToTopic(Constants.STATION_UPDATE)
                } catch (e: Exception) {
                    handleErrorX(e)
                }
            }
            try {
                splashRepository.subscribeToTopic(Constants.COMPANY_UPDATE)
                subscribeToTopicSuccessLiveMutable.postValue(true)
            } catch (e: Exception) {
                Log.e("exception", "company_update $e")
                try {
                    splashRepository.subscribeToTopic(Constants.COMPANY_UPDATE)
                } catch (e: Exception) {
                    handleErrorX(e)
                }
            }
        }
    }

}