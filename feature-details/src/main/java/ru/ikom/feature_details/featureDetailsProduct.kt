package ru.ikom.feature_details

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureDetailsProduct = module {
    viewModel<DetailsViewModel> {
        DetailsViewModel(get(), get(), get())
    }
}