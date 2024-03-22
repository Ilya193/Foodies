package ru.ikom.feature_catalog.di

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ikom.feature_catalog.data.CatalogRepositoryImpl
import ru.ikom.feature_catalog.domain.CatalogRepository
import ru.ikom.feature_catalog.domain.FetchCategoriesUseCase
import ru.ikom.feature_catalog.domain.FetchProductsUseCase
import ru.ikom.feature_catalog.presentation.CatalogViewModel

val featureCatalogModule = module {
    single<Gson> {
        Gson()
    }

    single<AssetManager> {
        get<Context>().assets
    }

    single<CatalogRepository> {
        CatalogRepositoryImpl(get(), get())
    }

    factory<FetchCategoriesUseCase> {
        FetchCategoriesUseCase(get())
    }

    factory<FetchProductsUseCase> {
        FetchProductsUseCase(get())
    }

    viewModel<CatalogViewModel> {
        CatalogViewModel(get(), get(), get(), get())
    }
}