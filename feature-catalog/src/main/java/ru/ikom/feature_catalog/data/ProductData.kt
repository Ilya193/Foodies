package ru.ikom.feature_catalog.data

import com.google.gson.annotations.SerializedName
import ru.ikom.feature_catalog.domain.ProductDomain

data class ProductData(
    @SerializedName("carbohydrates_per_100_grams")
    val carbohydratesPer100Grams: Double,
    @SerializedName("category_id")
    val categoryId: Int,
    val description: String,
    @SerializedName("energy_per_100_grams")
    val energyPer100Grams: Double,
    @SerializedName("fats_per_100_grams")
    val fatsPer100Grams: Double,
    val id: Int,
    val image: String,
    val measure: Int,
    @SerializedName("measure_unit")
    val measureUnit: String,
    val name: String,
    @SerializedName("price_current")
    val priceCurrent: Int,
    @SerializedName("price_old")
    val priceOld: Int?,
    @SerializedName("proteins_per_100_grams")
    val proteinsPer100Grams: Double,
    @SerializedName("tag_ids")
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