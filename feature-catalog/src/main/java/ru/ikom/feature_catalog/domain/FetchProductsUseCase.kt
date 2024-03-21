package ru.ikom.feature_catalog.domain

class FetchProductsUseCase(
    private val repository: CatalogRepository
) {

    suspend operator fun invoke(): LoadResult<List<ProductDomain>> = repository.fetchProducts()
}