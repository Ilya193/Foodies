package ru.ikom.feature_details.presentation

data class DetailsUiState(
    val product: CacheProductUi? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)