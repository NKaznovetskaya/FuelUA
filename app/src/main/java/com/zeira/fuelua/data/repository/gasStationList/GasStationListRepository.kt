package com.zeira.fuelua.data.repository.gasStationList

import com.zeira.fuelua.data.repository.retrofit.ApiDataSource
import com.zeira.fuelua.data.storage.FavoritesDao
import com.zeira.fuelua.domain.models.*

interface GasStationListRepository {

    // suspend fun getAllGasStation(): Flow<ArrayList<GasStationModel>>

    suspend fun addToFavorites(model: RealmFavoritesModel)

    suspend fun removeFromFavorites(id: String)

    suspend fun getFavorites(): ArrayList<RealmFavoritesModel>

    suspend fun getAllStation(): ArrayList<GasStationModelX>

    suspend fun getByFilter(filterModel: FilterModel): ArrayList<GasStationModelX>

    suspend fun createGasStation(createGasStationModel: CreateGasStationModel): CreateGasStationModel

    suspend fun getCreatedStation(id: String): ArrayList<CreateGasStationModel>

    suspend fun updateGasStation(id: Int, updateGasStationModel: UpdateGasStationModel): UpdateGasStationModel

    suspend fun removeGasStation(id: Int)
}

class GasStationListRepositoryImpl(private val dao: FavoritesDao, private val api: ApiDataSource) :
    GasStationListRepository {

//    override suspend fun getAllGasStation(): Flow<ArrayList<GasStationModel>> =
//        FirestoreDataSource().getAllGasStation()

    override suspend fun addToFavorites(model: RealmFavoritesModel) {
        dao.addToRealm(model)
    }

    override suspend fun removeFromFavorites(id: String) =
        dao.deleteFromRealm(id)

    override suspend fun getFavorites(): ArrayList<RealmFavoritesModel> = dao.findAll()

    override suspend fun getAllStation(): ArrayList<GasStationModelX> = api.getAllStation()

    override suspend fun getCreatedStation(id: String): ArrayList<CreateGasStationModel> = api.getCreatedStation(id)

    override suspend fun updateGasStation(id: Int, updateGasStationModel: UpdateGasStationModel): UpdateGasStationModel = api.updateGasStation(id, updateGasStationModel)

    override suspend fun createGasStation(createGasStationModel: CreateGasStationModel) =
        api.createGasStation(createGasStationModel = createGasStationModel)

    override suspend fun getByFilter(filterModel: FilterModel): ArrayList<GasStationModelX> =
        api.getByFilter(filterModel.toQueryMap())

    override suspend fun removeGasStation(id: Int) = api.removeGasStation(id)
}