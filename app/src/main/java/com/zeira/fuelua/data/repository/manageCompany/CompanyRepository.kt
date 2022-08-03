package com.zeira.fuelua.data.repository.manageCompany

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.zeira.fuelua.data.repository.retrofit.ApiDataSource
import com.zeira.fuelua.domain.models.CompanyModel
import com.zeira.fuelua.domain.models.CompanyModelX
import com.zeira.fuelua.domain.models.GasStationModelX
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine


interface CompanyRepository {

    suspend fun uploadCompanyImage(selectedImage: Uri?): String

    suspend fun updateCompanyData(oldCompanyModel: CompanyModelX, newCompanyModel: CompanyModelX)

    suspend fun getStation(): ArrayList<GasStationModelX>

    suspend fun getCompany(): List<CompanyModelX>

    suspend fun createCompany(companyModel: CompanyModelX)

}

class CompanyRepositoryImpl(private val api: ApiDataSource) : CompanyRepository {

    //add image to firebase storage and return image url
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun uploadCompanyImage(selectedImage: Uri?): String =
        suspendCancellableCoroutine { emitter ->
            var companyImageURL: String
            if (selectedImage != null) {
                val sRef: StorageReference =
                    FirebaseStorage.getInstance().reference.child("COMPANY_LOGO" + selectedImage.lastPathSegment)

                sRef.putFile(selectedImage)
                    .addOnSuccessListener { taskSnapshot ->
                        Log.i(
                            "Firebase Image URL",
                            taskSnapshot.metadata?.reference?.downloadUrl.toString()
                        )

                        taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                            Log.i("Downloadable Image URL", uri.toString())
                            companyImageURL = uri.toString()
                            emitter.resume(companyImageURL) {}

                        }
                    }
            }
        }

    override suspend fun updateCompanyData(
        oldCompanyModel: CompanyModelX,
        newCompanyModel: CompanyModelX
    ) {
        if (oldCompanyModel != newCompanyModel) {
            val id = newCompanyModel.id
            api.updateCompany(id, newCompanyModel)
        }
    }


    override suspend fun getStation(): ArrayList<GasStationModelX> = api.getAllStation()

    override suspend fun getCompany(): List<CompanyModelX> = api.getCompany()

    override suspend fun createCompany(companyModel: CompanyModelX) {
        api.createCompany(companyModel)
    }

}