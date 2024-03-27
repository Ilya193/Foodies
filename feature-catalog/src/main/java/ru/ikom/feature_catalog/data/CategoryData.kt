package ru.ikom.feature_catalog.data

import kotlinx.serialization.Serializable
import ru.ikom.feature_catalog.domain.CategoryDomain

@Serializable
data class CategoryData(
    val id: Int,
    val name: String
) {
    fun toCategoryDomain(): CategoryDomain =
        CategoryDomain(id, name)
}