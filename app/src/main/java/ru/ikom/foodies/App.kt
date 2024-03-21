package ru.ikom.foodies

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.ikom.feature_catalog.di.featureCatalogModule
import ru.ikom.feature_catalog.presentation.CatalogRouter

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, featureCatalogModule)
        }
    }
}

val appModule = module {
    val navigation = Navigation.Base()

    single<Navigation<Screen>> {
        navigation
    }

    factory<SplashRouter> {
        navigation
    }

    single<CatalogRouter> {
        navigation
    }

    viewModel<MainViewModel> {
        MainViewModel(get())
    }

    viewModel<SplashViewModel> {
        SplashViewModel(get())
    }
}