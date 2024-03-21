package ru.ikom.feature_catalog.domain

class FetchCategoriesUseCase(
    private val repository: CatalogRepository
) {

    suspend operator fun invoke(): LoadResult<List<CategoryDomain>> = repository.fetchCategories()
}