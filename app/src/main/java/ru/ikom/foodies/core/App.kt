package ru.ikom.foodies.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.ikom.feature_basket.di.featureBasketModule
import ru.ikom.feature_catalog.di.featureCatalogModule
import ru.ikom.feature_details.presentation.featureDetailsProduct

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, featureCatalogModule, featureDetailsProduct, featureBasketModule)
        }
    }
}