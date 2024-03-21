package ru.ikom.feature_catalog.presentation

import ru.ikom.feature_catalog.domain.CategoryDomain
import ru.ikom.feature_catalog.domain.ProductDomain

object Utils {
    fun CategoryDomain.toCategoryUi(selected: Boolean = false): CategoryUi =
        CategoryUi(id, name, selected)

    fun ProductDomain.toProductUi(): ProductUi =
        ProductUi(
            carbohydratesPer100Grams,
            categoryId,
            description,
            energyPer100Grams,
            fatsPer100Grams,
            id,
            image,
            measure,
            measureUnit,
            name,
            priceCurrent / 100,
            priceOld?.let { it / 100 },
            proteinsPer100Grams,
            tagIds
        )
}