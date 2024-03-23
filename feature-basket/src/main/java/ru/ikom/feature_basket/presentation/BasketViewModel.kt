package ru.ikom.feature_basket.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ikom.common.BaseViewModel
import ru.ikom.common.Storage
import ru.ikom.feature_basket.presentation.Utils.toCacheProductUi

class BasketViewModel(
    private val router: BasketRouter,
    private val storage: Storage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<BasketUiState>(router, BasketUiState(isLoading = true)) {

    private var products = mutableListOf<ProductUi>()
    fun fetchProducts() = viewModelScope.launch(dispatcher) {
        try {
            products = storage.read().map { it.toCacheProductUi() }.toMutableList()
            var sum = 0
            products.forEach {
                sum += it.priceCurrent * it.count
            }
            _uiState.value = BasketUiState(products = products, sum = sum)
        } catch (_: Exception) {
            _uiState.value = BasketUiState(isError = true)
        }
    }

    fun minus(product: ProductUi, index: Int) = viewModelScope.launch(dispatcher) {
        storage.update(product.copy(count = product.count - 1).toCacheProduct(), index)
        fetchProducts()
    }

    fun plus(product: ProductUi, index: Int) = viewModelScope.launch(dispatcher) {
        storage.update(product.copy(count = product.count + 1).toCacheProduct(), index)
        fetchProducts()
    }
}

data class BasketUiState(
    val products: List<ProductUi> = emptyList(),
    val sum: Int = 0,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)