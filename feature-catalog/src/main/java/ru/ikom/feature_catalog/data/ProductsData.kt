package ru.ikom.feature_catalog.data

class ProductsData : ArrayList<ProductData>() {
    fun toProductsDomain() = map { it.toProductDomain() }
}