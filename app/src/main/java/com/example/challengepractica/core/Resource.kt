package com.example.challengepractica.core

import java.lang.Exception

//Retornar 3 estados
sealed class Resource<out T> {
    class Loading<out T>: Resource<T>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Failure(val exception: Exception): Resource<Nothing>()
    }

