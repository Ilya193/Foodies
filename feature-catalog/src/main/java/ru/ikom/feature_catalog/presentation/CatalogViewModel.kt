package ru.ikom.feature_catalog.presentation

import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ikom.common.BaseViewModel
import ru.ikom.common.Storage
import ru.ikom.feature_catalog.domain.FetchCategoriesUseCase
import ru.ikom.feature_catalog.domain.FetchProductsUseCase
import ru.ikom.feature_catalog.domain.LoadResult
import ru.ikom.feature_catalog.presentation.Utils.toCategoryUi
import ru.ikom.feature_catalog.presentation.Utils.toProductUi
import java.util.Locale

class CatalogViewModel(
    private val router: CatalogRouter,
    private val categoriesUseCase: FetchCategoriesUseCase,
    private val productsUseCase: FetchProductsUseCase,
    private val gson: Gson,
    private val storage: Storage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<CatalogUiState>(router, CatalogUiState()) {

    private var categories = mutableListOf<CategoryUi>()
    private var products = mutableListOf<ProductUi>()
    private var showProducts = mutableListOf<ProductUi>()

    fun fetchData() = viewModelScope.launch(dispatcher) {
        _uiState.value = CatalogUiState(isLoading = true)
        categories = when (val res = categoriesUseCase()) {
            is LoadResult.Success -> res.data.mapIndexed { index, item ->
                if (index == 0) item.toCategoryUi(selected = true)
                else item.toCategoryUi()
            }.toMutableList()

            is LoadResult.Error -> mutableListOf()
        }

        products = when (val res = productsUseCase()) {
            is LoadResult.Success -> res.data.map { it.toProductUi() }.toMutableList()
            is LoadResult.Error -> mutableListOf()
        }

        showProducts = products.toMutableList()

        _uiState.value = CatalogUiState(
            categories.toList(),
            showProducts.toList(),
            isError = categories.isEmpty() && products.isEmpty()
        )
    }

    fun onClickCategory(item: CategoryUi) = viewModelScope.launch(dispatcher) {
        val index = categories.indexOf(item)
        if (!categories[index].selected) {
            for (i in 0..<categories.size) {
                categories[i] = if (i == index) categories[i].copy(selected = true)
                else categories[i].copy(selected = false)
            }
            _uiState.value = CatalogUiState(
                categories = categories.toList(),
                products = products.toList(),
            )
        }
    }

    fun onClickBuy(item: ProductUi, index: Int) = viewModelScope.launch(dispatcher) {
        showProducts[index] = showProducts[index].copy(count = item.count, buy = true)
        val state = _uiState.value
        _uiState.update {
            it.copy(
                categories = categories.toList(),
                products = showProducts.toList(),
                sum = if (state.sum == null) item.priceCurrent else state.sum + item.priceCurrent
            )
        }
    }

    fun onClickMinus(item: ProductUi, index: Int) = viewModelScope.launch(dispatcher) {
        _uiState.update {
            if (item.count == 1) {
                showProducts[index] = showProducts[index].copy(buy = false)
                val state = _uiState.value
                val sum = if (state.sum != null) {
                    val value = state.sum - item.priceCurrent
                    if (value == 0) null else value
                } else null
                it.copy(
                    categories = categories.toList(),
                    products = showProducts.toList(),
                    sum = sum
                )
            } else {
                showProducts[index] = showProducts[index].copy(count = item.count - 1)
                val state = _uiState.value
                it.copy(
                    categories = categories.toList(),
                    products = showProducts.toList(),
                    sum = state.sum?.let {
                        it - item.priceCurrent
                    }
                )
            }
        }
    }

    fun onClickPlus(item: ProductUi, index: Int) = viewModelScope.launch(dispatcher) {
        showProducts[index] = showProducts[index].copy(count = item.count + 1)
        val state = _uiState.value
        _uiState.update {
            it.copy(
                categories = categories.toList(),
                products = showProducts.toList(),
                sum = state.sum?.let {
                    it + item.priceCurrent
                }
            )
        }
    }

    fun changeMode(mode: Boolean) = viewModelScope.launch(dispatcher) {
        showProducts = products.toMutableList()
        _uiState.value = if (mode) CatalogUiState(searchMode = mode) else CatalogUiState(
            categories = categories.toList(),
            products = showProducts.toList(),
            searchMode = mode
        )
    }

    fun inputDish(dish: String) = viewModelScope.launch(dispatcher) {
        if (dish.isEmpty()) {
            _uiState.update { it.copy(products = emptyList(), nothingFound = NothingFound.Initial) }
        } else {
            showProducts.clear()
            products.forEach {
                if (dish in it.description.lowercase(Locale.getDefault())) {
                    showProducts.add(it)
                }
            }
            _uiState.update {
                it.copy(
                    products = showProducts.toList(),
                    nothingFound = if (showProducts.isEmpty()) NothingFound.Search else NothingFound.Initial
                )
            }
        }
    }

    fun filter(filterMode: List<Int>) = viewModelScope.launch(dispatcher) {
        if (filterMode.isEmpty()) {
            showProducts = products.toMutableList()
            _uiState.value = CatalogUiState(
                categories = categories.toList(),
                products = showProducts.toList(),
            )
        } else {
            showProducts.clear()
            products.forEach { product ->
                var add = false
                filterMode.forEach { mode ->
                    when (mode) {
                        1 -> add = 2 in product.tagIds
                        2 -> add = 4 in product.tagIds
                        3 -> add = product.priceOld != null
                    }
                }
                if (add) showProducts.add(product)
            }
            _uiState.value = CatalogUiState(
                categories = categories.toList(),
                products = showProducts.toList(),
                nothingFound = if (showProducts.isEmpty()) NothingFound.Filter else NothingFound.Initial,
                filterMode = filterMode.toList()
            )
        }
    }

    fun openDetails(item: ProductUi) = viewModelScope.launch(dispatcher) {
        router.openDetails(gson.toJson(item.toCacheProduct()))
    }

    fun add() = viewModelScope.launch(dispatcher) {
        val filteredProducts = showProducts.filter { it.buy }
        storage.save(filteredProducts.map { it.toCacheProduct() })
        router.openBasket()
    }
}