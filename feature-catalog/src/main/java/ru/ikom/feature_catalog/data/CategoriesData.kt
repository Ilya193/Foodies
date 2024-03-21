package ru.ikom.feature_catalog.data

import ru.ikom.feature_catalog.domain.CategoryDomain

class CategoriesData : ArrayList<CategoryData>() {
    fun toCategoriesDomain(): List<CategoryDomain> = map { it.toCategoryDomain() }
}