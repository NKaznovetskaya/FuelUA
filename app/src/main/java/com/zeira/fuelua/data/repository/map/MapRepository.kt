package com.zeira.fuelua.data.repository.map

import com.zeira.fuelua.data.repository.retrofit.ApiDataSource
import com.zeira.fuelua.domain.models.GasStationModelX


interface MapRepository {

//    suspend fun getAllGasStation() : Flow<ArrayList<GasStationModel>> //Firebase

    suspend fun getAllStation(): ArrayList<GasStationModelX>

}

class MapRepositoryImpl(private val api: ApiDataSource) : MapRepository{
//    override suspend fun getAllGasStation(): Flow<ArrayList<GasStationModel>> = FirestoreDataSource().getAllGasStation()

    override suspend fun getAllStation(): ArrayList<GasStationModelX> = api.getAllStation()

}