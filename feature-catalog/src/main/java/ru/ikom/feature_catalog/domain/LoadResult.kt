package ru.ikom.feature_catalog.domain

sealed class LoadResult<out T> {

    data class Success<T>(
        val data: T
    ): LoadResult<T>()

    data object Error: LoadResult<Nothing>()

}