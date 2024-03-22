package ru.ikom.feature_catalog.presentation

data class CategoryUi(
    val id: Int,
    val name: String,
    val selected: Boolean = false
)

data class ProductUi(
    val carbohydratesPer100Grams: Double,
    val categoryId: Int,
    val description: String,
    val energyPer100Grams: Double,
    val fatsPer100Grams: Double,
    val id: Int,
    val image: String,
    val measure: Int,
    val measureUnit: String,
    val name: String,
    val priceCurrent: Int,
    val priceOld: Int?,
    val proteinsPer100Grams: Double,
    val tagIds: List<Int>,
    val count: Int = 1,
    val buy: Boolean = false
)

sealed interface NothingFound {
    data object Filter : NothingFound
    data object Search : NothingFound

    data object Initial : NothingFound
}

data class CatalogUiState(
    val categories: List<CategoryUi> = emptyList(),
    val products: List<ProductUi> = emptyList(),
    val sum: Int? = null,
    val searchMode: Boolean = false,
    val nothingFound: NothingFound = NothingFound.Initial,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)