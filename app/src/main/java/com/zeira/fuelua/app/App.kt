package com.zeira.fuelua.app

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.zeira.fuelua.di.appModule
import com.zeira.fuelua.di.networkModule
import com.zeira.fuelua.utils.Constants
import com.zeira.fuelua.core.utils.ShowToastInterface
import com.zeira.fuelua.core.utils.ShowToastManager
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    val toastManager: ShowToastInterface = ShowToastManager(this)

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
        initRealm(this)

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, networkModule))
        }
    }

     fun initRealm(context: Context) {
        Realm.init(context)

        val configuration = RealmConfiguration.Builder()
            .name(Constants.FAV_TABLE)
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        Realm.setDefaultConfiguration(configuration)
    }
}