package com.example.challengepractica.repository

import com.example.challengepractica.application.AppConstants
import com.example.challengepractica.data.model.MovieList
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//generar peticiones de retrofit con webservice y un objeto retrofit
interface WebService {
    @GET("movie/upcoming")
    suspend fun  getUpcomingMovies(@Query ("api_key") apikey: String): MovieList
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query ("api_key") apikey: String): MovieList
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query ("api_key") apikey: String): MovieList
}

object RetrofitClient{

    //lazy (inicializa en el momento que se ejecuta y no antes)
    val webService by lazy {

        //convierto el json de respuesta en un objeto del modelo de dataSource
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)

    }
}