package com.zeira.fuelua.data.repository.retrofit

import com.zeira.fuelua.domain.models.CompanyModelX
import com.zeira.fuelua.domain.models.CreateGasStationModel
import com.zeira.fuelua.domain.models.GasStationModelX
import com.zeira.fuelua.domain.models.UpdateGasStationModel
import retrofit2.http.*

interface ApiInterface {

    @GET("station")
    suspend fun getAllStation(): ArrayList<GasStationModelX>

    @GET("user_company")
    suspend fun getCompany(): List<CompanyModelX>

    @GET("station/{id}/")
    suspend fun getCreatedStation(@Path("id") id: String): ArrayList<CreateGasStationModel>


    @POST("company/create/")
    suspend fun createCompany(@Body companyModel: CompanyModelX): CompanyModelX

    @POST("station/create/")
    suspend fun createStation(@Body createGasStationModel: CreateGasStationModel) : CreateGasStationModel

    @PUT("company/update/{id}")
    suspend fun updateCompany(
        @Path(value = "id", encoded = true) id: Int,
        @Body companyModel: CompanyModelX
    )

    @GET("station/")
    suspend fun getByFilter(
        @QueryMap(encoded = true) filter: Map<String, String>
    ): ArrayList<GasStationModelX>

    @PUT("station/update/{id}")
    suspend fun updateGasStation(@Path("id") id: Int, @Body updateGasStationModel: UpdateGasStationModel): UpdateGasStationModel

    @DELETE("station/update/{id}")
    suspend fun removeGasStation(@Path("id") id: Int)
}

class ApiDataSource(
    private val api: ApiInterface
) {
    suspend fun getAllStation() = api.getAllStation()

    suspend fun getCompany() = api.getCompany()

    suspend fun getCreatedStation(id: String) = api.getCreatedStation(id)

    suspend fun createGasStation(createGasStationModel: CreateGasStationModel) = api.createStation(createGasStationModel = createGasStationModel)

    suspend fun createCompany(companyModel: CompanyModelX) =
        api.createCompany(companyModel = companyModel)

    suspend fun updateCompany(id: Int, companyModel: CompanyModelX) =
        api.updateCompany(id, companyModel)

    suspend fun getByFilter(filter: Map<String, String>) = api.getByFilter(filter)

    suspend fun updateGasStation(id: Int, updateGasStationModel: UpdateGasStationModel) = api.updateGasStation(id, updateGasStationModel)

    suspend fun removeGasStation(id: Int) = api.removeGasStation(id)

}

