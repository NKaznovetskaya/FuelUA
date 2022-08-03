package com.zeira.fuelua.di

import com.zeira.fuelua.app.App
import com.zeira.fuelua.data.repository.bottomSheet.DetailsRepository
import com.zeira.fuelua.data.repository.bottomSheet.DetailsRepositoryImpl
import com.zeira.fuelua.data.repository.favorites.FavoritesRepository
import com.zeira.fuelua.data.repository.favorites.FavoritesRepositoryImpl
import com.zeira.fuelua.data.repository.gasStationList.GasStationListRepository
import com.zeira.fuelua.data.repository.gasStationList.GasStationListRepositoryImpl
import com.zeira.fuelua.data.repository.manageCompany.CompanyRepository
import com.zeira.fuelua.data.repository.manageCompany.CompanyRepositoryImpl
import com.zeira.fuelua.data.repository.map.MapRepository
import com.zeira.fuelua.data.repository.map.MapRepositoryImpl
import com.zeira.fuelua.data.repository.retrofit.ApiDataSource
import com.zeira.fuelua.data.repository.splash.SplashRepository
import com.zeira.fuelua.data.repository.splash.SplashRepositoryImpl
import com.zeira.fuelua.data.storage.FavoritesDao
import com.zeira.fuelua.data.storage.FavoritesDaoImpl
import com.zeira.fuelua.presentation.details.DetailsViewModel
import com.zeira.fuelua.presentation.favorites.FavoritesViewModel
import com.zeira.fuelua.presentation.gasStationList.GasStationListViewModel
import com.zeira.fuelua.presentation.map.MapViewModel
import com.zeira.fuelua.presentation.menu.company.authorization.PhoneAuthViewModel
import com.zeira.fuelua.presentation.menu.company.companySettings.ManageCompanyViewModel
import com.zeira.fuelua.presentation.splash.SplashViewModel
import com.zeira.fuelua.presentation.menu.company.createStation.CreateStationViewModel
import com.zeira.fuelua.presentation.menu.company.listStation.CompanyListStationViewModel
import com.zeira.fuelua.presentation.menu.company.updateStation.UpdateGasStationViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {


    viewModel<GasStationListViewModel> {
        GasStationListViewModel(get())
    }

    viewModel<MapViewModel> {
        MapViewModel(get())
    }

    viewModel<FavoritesViewModel> {
        FavoritesViewModel(get())
    }

    viewModel<DetailsViewModel> {
        DetailsViewModel(get())
    }

    viewModel<PhoneAuthViewModel> {
        PhoneAuthViewModel()
    }

    viewModel<ManageCompanyViewModel> {
        ManageCompanyViewModel(get())
    }

    viewModel<SplashViewModel> {
        SplashViewModel(get())
    }

    viewModel<CreateStationViewModel> {
        CreateStationViewModel(get(), get())
    }

    viewModel<CompanyListStationViewModel> {
        CompanyListStationViewModel(get())
    }

    viewModel<UpdateGasStationViewModel> {
        UpdateGasStationViewModel(get(), get())
    }

    fun provideFavoritesDao(): FavoritesDao = FavoritesDaoImpl()

    fun provideGasStationListRepository(
        dao: FavoritesDao,
        api: ApiDataSource
    ): GasStationListRepository =
        GasStationListRepositoryImpl(dao, api)

    factory { provideFavoritesDao() }
    single<GasStationListRepository> { provideGasStationListRepository(get(), get()) }
    single<MapRepository> { MapRepositoryImpl(get()) }
    single<FavoritesRepository> { FavoritesRepositoryImpl(get()) }
    single<DetailsRepository> { DetailsRepositoryImpl() }
    single<CompanyRepository> { CompanyRepositoryImpl(get()) }
    single { androidApplication() as App }
    single { (androidApplication() as App).toastManager }
    single<SplashRepository> { SplashRepositoryImpl() }
}