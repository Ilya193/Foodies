package ru.ikom.common

interface Storage {
    fun save(product: CacheProduct)
    fun save(products: List<CacheProduct>)

    fun delete(product: CacheProduct)

    fun read(): List<CacheProduct>
}