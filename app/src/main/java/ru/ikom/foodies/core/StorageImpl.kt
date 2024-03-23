package ru.ikom.foodies.core

import ru.ikom.common.CacheProduct
import ru.ikom.common.Storage

class StorageImpl : Storage {

    private val products = mutableListOf<CacheProduct>()

    override suspend fun save(product: CacheProduct) {
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

    override suspend fun save(products: List<CacheProduct>) {
        products.forEach {
            save(it)
        }
    }

    override suspend fun update(product: CacheProduct, index: Int) {
        if (product.count == 0) products.removeAt(index)
        else products[index] = product
    }

    override suspend fun read(): List<CacheProduct> = products
}