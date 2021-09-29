package com.example.challengepractica.data.remote

import com.example.challengepractica.application.AppConstants
import com.example.challengepractica.data.model.MovieList
import com.example.challengepractica.repository.WebService

//3 metodos que van a buscar info al servidor a traves de webservices
class RemoteMovieDataSource(private val webService: WebService) {  //crea una instancia de WebService

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.API_KEY)

    suspend fun getTopRateMovies():MovieList = webService.getTopRatedMovies(AppConstants.API_KEY)

    suspend fun getPopularMovies():MovieList = webService.getPopularMovies(AppConstants.API_KEY)
}