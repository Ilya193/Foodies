package ru.ikom.common

interface Storage {
    suspend fun save(product: CacheProduct)
    suspend fun save(products: List<CacheProduct>)
    suspend fun update(product: CacheProduct, index: Int)
    suspend fun read(): List<CacheProduct>
}