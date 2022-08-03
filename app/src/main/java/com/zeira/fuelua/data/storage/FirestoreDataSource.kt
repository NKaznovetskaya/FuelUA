package com.zeira.fuelua.data.storage

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.zeira.fuelua.domain.models.CurrencyType
import com.zeira.fuelua.domain.models.FuelInfo
import com.zeira.fuelua.domain.models.FuelType
import com.zeira.fuelua.domain.models.GasStationModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirestoreDataSource {

    companion object {
        const val COMPANY_ID = "companyId"
        const val GAS_STATION_ID = "gasStationId"
        const val GAS_STATION_COLLECTION = "gas_station"
        const val LOGO = "logo"
        const val ADDRESS = "address"
        const val CREATED_AT = "createdAt"
        const val UPDATED_AT = "updatedAt"
        const val GEODATA = "geoData"
        const val FUEL_INFO = "fuelInfo"
        const val CURRENCY_TYPE = "currency"
        const val WORK_SCHEDULE = "workSchedule"
        const val ADDITIONAL_INFO = "additionalInfo"
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getAllGasStation(): Flow<ArrayList<GasStationModel>> = callbackFlow {
        val db = Firebase.firestore
        db.collection(GAS_STATION_COLLECTION)
            .get()
            .addOnSuccessListener {
                val documents = it.documents
                val gasStations = arrayListOf<GasStationModel>()
                documents.forEach { document ->
                    val companyId = document[COMPANY_ID] as String?
                    val gasStationId = document[GAS_STATION_ID] as String?
                    val logo = document[LOGO] as String?
                    val address = document[ADDRESS] as String?

                    val createdAtTimestamp = document[CREATED_AT] as Timestamp?
                    val createdAt = createdAtTimestamp?.seconds

                    val updatedAtTimestamp = document[UPDATED_AT] as Timestamp?
                    val updatedAt = updatedAtTimestamp?.seconds

                    val workSchedule = document[WORK_SCHEDULE] as String?
                    val additionalInfo = document[ADDITIONAL_INFO] as String?

                    val geoPoint = document[GEODATA] as GeoPoint?
                    val lat = geoPoint?.latitude
                    val lng = geoPoint?.longitude

                    val fuelInfoFBArray = document[FUEL_INFO] as ArrayList<*>
                    val fuelInfoArray = mutableListOf<FuelInfo>()

                    fuelInfoFBArray.forEach {
                        val fuelInfoString = it as String
                        val fuelInfoStrings = fuelInfoString.split("/")

                        val type = FuelType.values().find { it.name.lowercase() == fuelInfoStrings[0] }
                        val info = fuelInfoStrings[1]
                        val price = fuelInfoStrings[2]
                        fuelInfoArray.add(FuelInfo(info, price, type))
                    }

                    val currencyTypeString = document[CURRENCY_TYPE] as String?
                    val currencyType = CurrencyType.values()
                        .find { it.name.lowercase() == currencyTypeString?.lowercase() } ?: CurrencyType.UAH

                    gasStations.add(GasStationModel(companyId = companyId, gasStationId = gasStationId, logo = logo,
                        latitude = lat, longitude = lng, address = address, workSchedule = workSchedule, additionalInfo = additionalInfo, fuelInfo = fuelInfoArray,
                        createdAt = createdAt, updatedAt = updatedAt, currencyType = currencyType))


                    trySendBlocking(gasStations)
                }
            }
            .addOnFailureListener {
                Log.d("FirestoreDataSource", "error: ${it.message}")
                trySendBlocking(arrayListOf<GasStationModel>())
            }

        awaitClose()
    }
}