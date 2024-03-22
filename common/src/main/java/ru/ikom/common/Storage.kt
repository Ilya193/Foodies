package ru.ikom.common

interface Storage {
    fun save(product: CacheProduct)
    fun save(products: List<CacheProduct>)
    fun read(): List<CacheProduct>
}