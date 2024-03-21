package ru.ikom.feature_catalog.data

import ru.ikom.feature_catalog.domain.CategoryDomain

data class CategoryData(
    val id: Int,
    val name: String
) {
    fun toCategoryDomain(): CategoryDomain =
        CategoryDomain(id, name)
}