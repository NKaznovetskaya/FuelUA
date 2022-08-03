package com.zeira.fuelua.data.repository.bottomSheet

import com.zeira.fuelua.data.storage.FavoritesDaoImpl
import com.zeira.fuelua.domain.models.RealmFavoritesModel

interface DetailsRepository {

    suspend fun addToFavorites(model: RealmFavoritesModel)

    suspend fun removeFromFavorites(id: String)

    suspend fun getFavorites(): ArrayList<RealmFavoritesModel>

}

class DetailsRepositoryImpl : DetailsRepository {
    override suspend fun addToFavorites(model: RealmFavoritesModel) {
        FavoritesDaoImpl().addToRealm(model)
    }

    override suspend fun removeFromFavorites(id: String) {
        FavoritesDaoImpl().deleteFromRealm(id)
    }

    override suspend fun getFavorites(): ArrayList<RealmFavoritesModel> = FavoritesDaoImpl().findAll()

}