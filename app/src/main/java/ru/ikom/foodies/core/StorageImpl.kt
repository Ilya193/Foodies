package ru.ikom.foodies.core

import ru.ikom.common.CacheProduct
import ru.ikom.common.Storage

class StorageImpl : Storage {

    private val products = mutableListOf<CacheProduct>()

    override fun save(product: CacheProduct) {
        var add = false
        products.forEachIndexed { index, cacheProduct ->
            if (cacheProduct.id == product.id) {
                add = true
                products[index] = cacheProduct.copy(count = cacheProduct.count + product.count)
            }
        }
        if (!add)
            products.add(product)
    }

    override fun save(products: List<CacheProduct>) {
        products.forEach {
            save(it)
        }
    }

    override fun delete(product: CacheProduct) {
        products.remove(product)
    }

    override fun read(): List<CacheProduct> = products
}