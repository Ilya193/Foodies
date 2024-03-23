package ru.ikom.feature_basket.presentation

import ru.ikom.common.CacheProduct

object Utils {

    fun CacheProduct.toCacheProductUi(): ProductUi =
        ProductUi(
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