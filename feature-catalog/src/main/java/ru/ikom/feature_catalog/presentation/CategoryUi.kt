package ru.ikom.feature_catalog.presentation

import ru.ikom.common.CacheProduct

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
) {
    fun toCacheProduct(): CacheProduct =
        CacheProduct(
            carbohydratesPer100Grams,
            description,
            energyPer100Grams,
            fatsPer100Grams,
            id,
            image,
            measure,
            measureUnit,
            name,
            priceCurrent,
            priceOld,
            proteinsPer100Grams,
            count
        )
}

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
    val filterMode: List<Int> = emptyList(),
    val nothingFound: NothingFound = NothingFound.Initial,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)