package com.example.challengepractica.data.local

import com.example.challengepractica.application.AppConstants
import com.example.challengepractica.data.model.MovieEntity
import com.example.challengepractica.data.model.MovieList
import com.example.challengepractica.data.model.toMovieList
import com.example.challengepractica.data.remote.RemoteMovieDataSource

//3 metodos que van a buscar info a la DB a traves de MovieDao
class LocalMovieDataSource(private val movieDao: MovieDao) {
        suspend fun getUpcomingMovies(): MovieList {
        //retornar una lista de MovieEntity como MovieList
        return movieDao.getAllMovies().filter { it.movie_type == "upcoming" }.toMovieList()
    }

    suspend fun getTopRateMovies(): MovieList {
        //retornar una lista de MovieEntity como MovieList
        return movieDao.getAllMovies().filter { it.movie_type == "toprated" }.toMovieList()
    }

    suspend fun getPopularMovies(): MovieList {
        //retornar una lista de MovieEntity como MovieList
        return movieDao.getAllMovies().filter { it.movie_type == "popular" }.toMovieList()
    }

    suspend fun saveMovie(movie: MovieEntity){
        movieDao.saveMovie(movie)
    }
}