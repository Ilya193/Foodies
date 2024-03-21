package ru.ikom.feature_catalog.domain

interface CatalogRepository {
    suspend fun fetchCategories(): LoadResult<List<CategoryDomain>>
    suspend fun fetchProducts(): LoadResult<List<ProductDomain>>
}