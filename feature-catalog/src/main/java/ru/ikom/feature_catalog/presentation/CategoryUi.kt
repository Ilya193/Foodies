package ru.ikom.feature_catalog.presentation

data class CategoryUi(
    val id: Int,
    val name: String,
    val selected: Boolean = false
)

data class CatalogUiState(
    val categories: List<CategoryUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: Boolean = false
)