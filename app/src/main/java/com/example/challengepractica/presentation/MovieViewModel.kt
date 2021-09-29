package com.example.challengepractica.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.challengepractica.core.Resource
import com.example.challengepractica.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

//conecta el repositorio con la vista
class MovieViewModel(private val repo: MovieRepository) : ViewModel() {

    //Dispatchers (hilo de ejecucion)
    //IO (tipo de hilo para ejecutar tareas en segundo plano)
    //Devuelve un liveDataScope que es un resource de MovieList
    //la funcion va a retornar 3 listas de peliculas
    fun fetchMainScreenMovies() = liveData(Dispatchers.IO) {
        //emitir un valor a la vista
        emit(Resource.Loading())

        //ejecutar corrutina que va a traer la info del servidor
        //Triple nos permite ejecutar 3 funciones
        //a partir de 4 funciones es "Ntuple4" en vez de "Triple" y
        //data class NTuple4<T1, T2, T3, T4>(val t1 : T1,val t2: T2,val t3: T3,val t4: T4)
        try {
            emit(
                Resource.Success(
                    Triple(
                        repo.getUpcomingMovies(),
                        repo.getTopRatedMovies(),
                        repo.getPopularMovies()
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class MovieViewModelFactory(private val repo: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }

}

