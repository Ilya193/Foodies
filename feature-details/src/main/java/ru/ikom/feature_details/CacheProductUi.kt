package ru.ikom.feature_details

data class CacheProductUi(
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
    val proteinsPer100Grams: Double,
)