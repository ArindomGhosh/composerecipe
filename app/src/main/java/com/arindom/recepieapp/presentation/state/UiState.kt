package com.arindom.recepieapp.presentation.state

import java.lang.Exception
import  com.arindom.recepieapp.domain.Result

data class UiState<T>(
    val loading: Boolean = false,
    val exception: Exception? = null,
    val data: T? = null
) {
    val hasError: Boolean
        get() = exception != null

    val initialLoad: Boolean
        get() = data != null && loading && !hasError
}


fun <T> UiState<T>.copyWithResult(value: Result<T>): UiState<T> {
    return when (value) {
        is Result.Success -> copy(false, null, value.data)
        is Result.Failure -> copy(false, exception, null)
    }
}