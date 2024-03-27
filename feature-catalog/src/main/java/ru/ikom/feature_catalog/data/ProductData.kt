package ru.ikom.feature_catalog.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.ikom.feature_catalog.domain.ProductDomain

@Serializable
data class ProductData(
    @SerialName("carbohydrates_per_100_grams")
    val carbohydratesPer100Grams: Double,
    @SerialName("category_id")
    val categoryId: Int,
    val description: String,
    @SerialName("energy_per_100_grams")
    val energyPer100Grams: Double,
    @SerialName("fats_per_100_grams")
    val fatsPer100Grams: Double,
    val id: Int,
    val image: String,
    val measure: Int,
    @SerialName("measure_unit")
    val measureUnit: String,
    val name: String,
    @SerialName("price_current")
    val priceCurrent: Int,
    @SerialName("price_old")
    val priceOld: Int?,
    @SerialName("proteins_per_100_grams")
    val proteinsPer100Grams: Double,
    @SerialName("tag_ids")
    val tagIds: List<Int>,
) {
    fun toProductDomain(): ProductDomain =
        ProductDomain(
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
            priceCurrent,
            priceOld,
            proteinsPer100Grams,
            tagIds
        )
}