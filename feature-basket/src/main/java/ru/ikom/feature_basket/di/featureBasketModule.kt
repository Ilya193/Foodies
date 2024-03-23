package ru.ikom.feature_basket.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ikom.feature_basket.presentation.BasketViewModel

val featureBasketModule = module {
    viewModel<BasketViewModel> {
        BasketViewModel(get(), get())
    }
}