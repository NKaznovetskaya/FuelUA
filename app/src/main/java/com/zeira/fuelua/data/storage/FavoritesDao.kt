package com.zeira.fuelua.data.storage

import android.util.Log
import com.zeira.fuelua.domain.models.RealmFavoritesModel
import io.realm.Realm


interface FavoritesDao {

    suspend fun addToRealm(model: RealmFavoritesModel)

    suspend fun findAll(): ArrayList<RealmFavoritesModel>

    suspend fun deleteFromRealm(id: String)

}

class FavoritesDaoImpl : FavoritesDao {
    override suspend fun addToRealm(model: RealmFavoritesModel) {
        try {
            Realm.getDefaultInstance().use { realm ->
                realm.executeTransaction {
                    it.insertOrUpdate(model)
                }
            }
            Log.i("AddToRealm", "Added successfully")
        } catch (e: Exception) {
            Log.d("AddToRealm", e.message.toString())

        }
    }

    override suspend fun findAll(): ArrayList<RealmFavoritesModel> {
        var list: ArrayList<RealmFavoritesModel>
        Realm.getDefaultInstance().use { realm ->
            list = realm.copyFromRealm(
                realm.where(RealmFavoritesModel::class.java).findAll()
            ) as ArrayList<RealmFavoritesModel>
        }
        return list
    }

    override suspend fun deleteFromRealm(id: String) {
        Realm.getDefaultInstance().use { realm ->
            val model =
                realm.where(RealmFavoritesModel::class.java).equalTo("gasStationId", id).findFirst()
            realm.executeTransaction {
                model?.deleteFromRealm()
                Log.i("RemoveFromRealm", "Remove successfully")
            }
        }
    }


}