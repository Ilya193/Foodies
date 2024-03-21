package ru.ikom.feature_catalog.presentation

import ru.ikom.feature_catalog.domain.CategoryDomain

object Utils {
    fun CategoryDomain.toCategoryUi(selected: Boolean = false): CategoryUi=
        CategoryUi(id, name, selected)
}