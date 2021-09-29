package com.example.challengepractica.repository

import com.example.challengepractica.data.model.MovieList

//se definen los metodos que se van a usar del dataSource
interface MovieRepository {

    suspend fun getUpcomingMovies(): MovieList
    suspend fun getTopRatedMovies(): MovieList
    suspend fun getPopularMovies(): MovieList
}