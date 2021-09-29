package com.example.challengepractica.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.challengepractica.data.model.MovieEntity

//Instancia Room que va a utilizar el DAO y la Entidad creados
//se indica cual va a ser la/s base de datos a utilizar
@Database(entities = [MovieEntity::class], version = 1)

//Debe ser abstracta para poder acceder a los datos abstractos
//y debe extender de RoomDatabase
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    //crear instancia de Room
    companion object {
        //nos aseguramos que solo haya una instancia
        private var INSTANCE: AppDatabase? = null

        //recibe un context y retorna un AppDatabase
        fun getDatabase(context: Context): AppDatabase {
            //si INSTANCE es nulo crea la instancia de Room y retorna un AppDatabase no nula
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "movie_table"
            ).build()
            return INSTANCE!!
        }

        //Destruir instancia
        fun destroyInstance(){
            INSTANCE = null
        }
    }
 }