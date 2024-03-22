package ru.ikom.foodies.core

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ikom.common.Storage
import ru.ikom.feature_catalog.presentation.CatalogRouter
import ru.ikom.foodies.presentation.MainViewModel
import ru.ikom.foodies.presentation.SplashRouter
import ru.ikom.foodies.presentation.SplashViewModel

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

    single<Storage> {
        StorageImpl()
    }

    viewModel<MainViewModel> {
        MainViewModel(get())
    }

    viewModel<SplashViewModel> {
        SplashViewModel(get())
    }
}