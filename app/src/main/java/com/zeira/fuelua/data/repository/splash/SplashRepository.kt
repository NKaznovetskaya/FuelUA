package com.zeira.fuelua.data.repository.splash

import com.zeira.fuelua.utils.MyFirebaseMessagingService

interface SplashRepository {

    suspend fun subscribeToTopic(topic: String)

}

class SplashRepositoryImpl() : SplashRepository {
    override suspend fun subscribeToTopic(topic: String) {
        MyFirebaseMessagingService.subscribeTopic(topic)
    }

}