package ru.ikom.feature_basket.presentation

import ru.ikom.common.CacheProduct

data class ProductUi(
    val carbohydratesPer100Grams: Double,
    val description: String,
    val energyPer100Grams: Double,
    val fatsPer100Grams: Double,
    val id: Int,
    val image: String,
    val measure: Int,
    val measureUnit: String,
    val name: String,
    val priceCurrent: Int,
    val priceOld: Int? = null,
    val proteinsPer100Grams: Double,
    val count: Int = 1,
    val sum: Int = 0,
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