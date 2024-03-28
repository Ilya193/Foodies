package ru.ikom.feature_details.presentation

import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import ru.ikom.common.BaseViewModel
import ru.ikom.common.Storage

class DetailsViewModel(
    private val router: DetailsRouter,
    private val data: String,
    private val storage: Storage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<DetailsUiState>(router, DetailsUiState(isLoading = true)) {

    init {
        decode()
    }

    fun decode() = viewModelScope.launch(dispatcher) {
        try {
            _uiState.value = DetailsUiState(product = Json.decodeFromString<CacheProductUi>(data))
        } catch (_: Exception) {
            _uiState.value = DetailsUiState(isError = true)
        }
    }

    fun add(product: CacheProductUi) = viewModelScope.launch(dispatcher) {
        storage.save(product.toCacheProduct())
        router.openBasket()
    }

    fun pop() = viewModelScope.launch(dispatcher) { router.comeback() }
}