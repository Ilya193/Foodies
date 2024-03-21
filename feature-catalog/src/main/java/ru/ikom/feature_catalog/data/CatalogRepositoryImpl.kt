package ru.ikom.feature_catalog.data

import android.content.res.AssetManager
import com.google.gson.Gson
import ru.ikom.feature_catalog.domain.CatalogRepository
import ru.ikom.feature_catalog.domain.CategoryDomain
import ru.ikom.feature_catalog.domain.LoadResult
import ru.ikom.feature_catalog.domain.ProductDomain
import java.io.InputStreamReader

class CatalogRepositoryImpl(
    private val assetManager: AssetManager,
    private val gson: Gson
) : CatalogRepository {
    override suspend fun fetchCategories(): LoadResult<List<CategoryDomain>> {
        return try {
            val stream = assetManager.open("categories.json")
            val reader = InputStreamReader(stream)
            LoadResult.Success(gson.fromJson(reader, CategoriesData::class.java).toCategoriesDomain())
        } catch (e: Exception) {
            LoadResult.Error
        }
    }

    override suspend fun fetchProducts(): LoadResult<List<ProductDomain>> {
        return try {
            val stream = assetManager.open("products.json")
            val reader = InputStreamReader(stream)
            LoadResult.Success(gson.fromJson(reader, ProductsData::class.java).toProductsDomain())
        } catch (e: Exception) {
            LoadResult.Error
        }
    }
}