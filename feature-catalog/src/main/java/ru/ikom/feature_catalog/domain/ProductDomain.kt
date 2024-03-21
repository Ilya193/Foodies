package ru.ikom.feature_catalog.domain

data class ProductDomain(
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
    val tagIds: List<Int>
)