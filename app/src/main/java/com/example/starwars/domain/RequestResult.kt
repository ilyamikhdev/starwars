package com.example.starwars.domain

sealed class RequestResult<out T : Any> {
    data class Success<T : Any>(val data: T) : RequestResult<T>()
    data class Error(val text: String) : RequestResult<Nothing>()
}