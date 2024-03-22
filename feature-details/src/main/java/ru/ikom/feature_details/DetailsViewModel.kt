package ru.ikom.feature_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ikom.common.Storage

class DetailsViewModel(
    private val gson: Gson,
    private val storage: Storage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState(isLoading = true))
    val uiState: StateFlow<DetailsUiState> get() = _uiState.asStateFlow()

    fun init(data: String) = viewModelScope.launch(dispatcher) {
        try {
            _uiState.value = DetailsUiState(product = gson.fromJson(data, CacheProductUi::class.java))
        } catch (_: Exception) {
            _uiState.value = DetailsUiState(isError = true)
        }
    }
}

data class DetailsUiState(
    val product: CacheProductUi? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)