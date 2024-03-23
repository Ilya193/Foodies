package ru.ikom.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S>(
    private val router: Router,
    initialState: S,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    protected val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> get() = _uiState.asStateFlow()

    fun comeback() = viewModelScope.launch(dispatcher) { router.comeback() }

}