package ru.ikom.feature_catalog.presentation

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private var categories = mutableListOf<CategoryUi>()
    private var products = mutableListOf<ProductUi>()
    private var showProducts = mutableListOf<ProductUi>()

    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

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
        val state = _uiState.value
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

    fun onClickProduct(item: ProductUi, index: Int) = viewModelScope.launch(dispatcher) {
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
            _uiState.value = CatalogUiState(products = emptyList())
        }
        else {
            showProducts.clear()
            products.forEach {
                if (dish in it.description.lowercase(Locale.getDefault())) {
                    showProducts.add(it)
                }
            }
            _uiState.update {
                it.copy(products = showProducts.toList())
            }
        }
    }
}