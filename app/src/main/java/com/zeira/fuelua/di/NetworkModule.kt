package com.zeira.fuelua.di


import com.zeira.fuelua.BuildConfig
import com.zeira.fuelua.data.repository.retrofit.ApiDataSource
import com.zeira.fuelua.data.repository.retrofit.ApiInterface
import com.zeira.fuelua.utils.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideApi(get()) }
    single { provideRetrofit(get()) }
    single { ApiDataSource(get()) }

}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.API_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(AuthInterceptor())
        .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()
}

fun provideApi(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)
