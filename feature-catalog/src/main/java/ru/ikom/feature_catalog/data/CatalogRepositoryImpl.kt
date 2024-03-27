package ru.ikom.feature_catalog.data

import android.content.res.AssetManager
import kotlinx.serialization.json.Json
import ru.ikom.feature_catalog.domain.CatalogRepository
import ru.ikom.feature_catalog.domain.CategoryDomain
import ru.ikom.feature_catalog.domain.LoadResult
import ru.ikom.feature_catalog.domain.ProductDomain
import java.io.BufferedReader

class CatalogRepositoryImpl(
    private val assetManager: AssetManager,
) : CatalogRepository {
    override suspend fun fetchCategories(): LoadResult<List<CategoryDomain>> {
        return try {
            val stream = assetManager.open("categories.json")
            val str = stream.bufferedReader().use(BufferedReader::readText)
            LoadResult.Success(Json.decodeFromString<List<CategoryData>>(str).map { it.toCategoryDomain() })
        } catch (e: Exception) {
            LoadResult.Error
        }
    }

    override suspend fun fetchProducts(): LoadResult<List<ProductDomain>> {
        return try {
            val stream = assetManager.open("products.json")
            val str = stream.bufferedReader().use(BufferedReader::readText)
            LoadResult.Success(Json.decodeFromString<List<ProductData>>(str).map { it.toProductDomain() })
        } catch (e: Exception) {
            LoadResult.Error
        }
    }
}