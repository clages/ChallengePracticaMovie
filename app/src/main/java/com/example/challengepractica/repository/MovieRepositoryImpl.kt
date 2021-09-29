package com.example.challengepractica.repository

import com.example.challengepractica.core.InternetCheck
import com.example.challengepractica.data.local.LocalMovieDataSource
import com.example.challengepractica.data.model.MovieList
import com.example.challengepractica.data.model.toMovieEntity
import com.example.challengepractica.data.remote.RemoteMovieDataSource

//implemetar los metodos que van al datasource a buscar la info
//tanto al remoto como al local
//Asignamos el tipo de pelicula
class MovieRepositoryImpl(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource
) : MovieRepository {

    override suspend fun getUpcomingMovies(): MovieList {
        //verificar si hay conexion de internet
        return if (InternetCheck.isNetworkAvailable()) {
            //acceder a los resultados de a lista de peliculas
            //iterar por cada una de las peliculas y guardarla en la DB local
            dataSourceRemote.getUpcomingMovies().results.forEach { movie ->
                //como saveMovie recibe un MovieEntity hay que llevarlo a ese tipo
                //para obtenerlas pasamos el tipo de pelicula
                dataSourceLocal.saveMovie(movie.toMovieEntity("upcoming"))
            }
            dataSourceLocal.getUpcomingMovies()
        } else {
            dataSourceLocal.getUpcomingMovies()
        }
    }

    override suspend fun getTopRatedMovies(): MovieList {
        //verificar si hay conexion de internet
        return if (InternetCheck.isNetworkAvailable()) {
            //acceder a los resultados de a lista de peliculas
            //iterar por cada una de las peliculas y guardarla en la DB local
            dataSourceRemote.getTopRateMovies().results.forEach { movie ->
                //como saveMovie recibe un MovieEntity hay que llevarlo a ese tipo
                //para obtenerlas pasamos el tipo de pelicula
                dataSourceLocal.saveMovie(movie.toMovieEntity("toprated"))
            }
            return dataSourceLocal.getTopRateMovies()
        } else {
            dataSourceLocal.getTopRateMovies()
        }
    }

    override suspend fun getPopularMovies(): MovieList {
        //verificar si hay conexion de internet
        return if (InternetCheck.isNetworkAvailable()) {
            //acceder a los resultados de a lista de peliculas
            //iterar por cada una de las peliculas y guardarla en la DB local
            dataSourceRemote.getPopularMovies().results.forEach { movie ->
                //como saveMovie recibe un MovieEntity hay que llevarlo a ese tipo
                //para obtenerlas pasamos el tipo de pelicula
                dataSourceLocal.saveMovie(movie.toMovieEntity("popular"))
            }
            return dataSourceLocal.getPopularMovies()
        } else {
            dataSourceLocal.getPopularMovies()
        }
    }
}