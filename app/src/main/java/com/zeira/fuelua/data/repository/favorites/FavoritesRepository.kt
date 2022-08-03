package com.zeira.fuelua.data.repository.favorites

import com.zeira.fuelua.data.repository.retrofit.ApiDataSource
import com.zeira.fuelua.data.storage.FavoritesDaoImpl
import com.zeira.fuelua.domain.models.GasStationModelX
import com.zeira.fuelua.domain.models.RealmFavoritesModel

interface FavoritesRepository {


//    suspend fun getAllStation(): Flow<ArrayList<GasStationModel>> // Firebase

    suspend fun getAllStation(): ArrayList<GasStationModelX>

    suspend fun getFavorites() : ArrayList<RealmFavoritesModel>

    suspend fun removeFromFavorites(id: String)

}

class FavoritesRepositoryImpl(private val api: ApiDataSource) : FavoritesRepository {
//    override suspend fun getAllStation(): Flow<ArrayList<GasStationModel>> =
//        FirestoreDataSource().getAllGasStation()

    override suspend fun getFavorites(): ArrayList<RealmFavoritesModel> = FavoritesDaoImpl().findAll()

    override suspend fun getAllStation(): ArrayList<GasStationModelX> = api.getAllStation()

    override suspend fun removeFromFavorites(id: String) {
        FavoritesDaoImpl().deleteFromRealm(id)
    }

}