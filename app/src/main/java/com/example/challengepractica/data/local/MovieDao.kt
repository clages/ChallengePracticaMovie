package com.example.challengepractica.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.challengepractica.data.model.MovieEntity

//ABM de la base de datos ROOM
@Dao
interface MovieDao {

    @Query("SELECT * FROM movieentity")
    //corutina que devuelve una lista con el formato de la DB room
    suspend fun getAllMovies(): List<MovieEntity>

    //onConflict(indicamos accion ante un conflicto, por ej. Id duplicado)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //corutina que recibe una lista con el formato de la DB room
    suspend fun saveMovie(movie: MovieEntity)


}