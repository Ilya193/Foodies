package ru.ikom.foodies

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import ru.ikom.common.CacheProduct
import ru.ikom.common.Storage
import ru.ikom.foodies.core.StorageImpl

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class StorageUnitTest {
    @Test
    fun `basket is empty when created`() = runBlocking {
        val storage: Storage = StorageImpl()
        assertEquals(mutableListOf<CacheProduct>(), storage.read())
    }

    @Test
    fun `adding a product to basket`() = runBlocking {
        val storage: Storage = StorageImpl()
        val product = CacheProduct(
            100.0,
            "description",
            100.0,
            100.0,
            0, "image",
            0,
            "", "name", 100, null, 100.0
        )
        storage.save(product)
        assertEquals(listOf(product), storage.read())
    }

    @Test
    fun `adding the same product multiple times to cart`() = runBlocking {
        val storage: Storage = StorageImpl()
        val product = CacheProduct(
            100.0,
            "description",
            100.0,
            100.0,
            0, "image",
            0,
            "", "name", 100, null, 100.0
        )
        storage.save(product)
        storage.save(product)
        val expectedProduct = product.copy(count = 2)
        assertEquals(listOf(expectedProduct), storage.read())
    }

    @Test
    fun `adding a list of products to the cart`() = runBlocking {
        val storage: Storage = StorageImpl()
        val product1 = CacheProduct(
            100.0,
            "description",
            100.0,
            100.0,
            0, "image",
            0,
            "", "name", 100, null, 100.0
        )
        val product2 = CacheProduct(
            100.0,
            "description",
            100.0,
            100.0,
            0, "image",
            0,
            "", "name", 100, null, 100.0
        )
        val product3 = CacheProduct(
            100.0,
            "description",
            100.0,
            100.0,
            1, "image",
            0,
            "", "name", 100, null, 100.0
        )
        storage.save(product1)
        storage.save(product2)
        storage.save(product3)
        val expectedItem = product1.copy(count = 2)
        assertEquals(listOf(expectedItem, product3), storage.read())
    }

    @Test
    fun `product update`() = runBlocking {
        val storage: Storage = StorageImpl()
        val product1 = CacheProduct(
            100.0,
            "description",
            100.0,
            100.0,
            0, "image",
            0,
            "", "name", 100, null, 100.0, count = 4
        )
        storage.save(product1)
        assertEquals(listOf(product1), storage.read())
        val expectedItem = product1.copy(count = 3)
        storage.update(expectedItem, 0)
        assertEquals(listOf(expectedItem), storage.read())
    }
}