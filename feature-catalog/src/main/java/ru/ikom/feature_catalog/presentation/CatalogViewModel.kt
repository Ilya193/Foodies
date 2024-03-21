package ru.ikom.feature_catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ikom.feature_catalog.domain.FetchCategoriesUseCase
import ru.ikom.feature_catalog.domain.LoadResult
import ru.ikom.feature_catalog.presentation.Utils.toCategoryUi

class CatalogViewModel(
    private val router: CatalogRouter,
    private val categoriesUseCase: FetchCategoriesUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    fun fetchData() = viewModelScope.launch(dispatcher) {
        _uiState.value = CatalogUiState(isLoading = true)
        val categories = when (val res = categoriesUseCase()) {
            is LoadResult.Success -> CatalogUiState(categories = res.data.mapIndexed { index, item ->
                if (index == 0) item.toCategoryUi(selected = true)
                else item.toCategoryUi()
            })

            is LoadResult.Error -> CatalogUiState(error = true)
        }
        _uiState.value = categories
    }

    fun onClickCategory(item: CategoryUi) = viewModelScope.launch(dispatcher) {
        val categories = _uiState.value.categories.toMutableList()
        val index = categories.indexOf(item)
        if (!categories[index].selected) {
            for (i in 0..<categories.size) {
                categories[i] = if (i == index) categories[i].copy(selected = true)
                else categories[i].copy(selected = false)
            }
            _uiState.value = CatalogUiState(categories = categories)
        }
    }
}