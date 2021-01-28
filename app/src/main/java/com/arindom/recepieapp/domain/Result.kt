package com.arindom.recepieapp.domain

import java.lang.Exception

sealed class Result<T> {
    data class Failure<T>(val exception: Exception) : Result<T>()
    data class Success<T>(val data: T) : Result<T>()
}