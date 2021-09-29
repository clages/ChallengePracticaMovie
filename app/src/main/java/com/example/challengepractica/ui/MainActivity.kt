package com.example.challengepractica.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.challengepractica.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

//ui vistas
//core cosas internas de la aplicacion

/*
-MovieFragment
-va al viewmodel (MovieViewModel)
-pregunta si hay datos en el servidor (fetchUpcomingMovies)
-va al repo y luego a la implementacion, luego al datasource y luego al webservice con la ApiKey
-emite un resultado de carga
-vuelve a MovieFragment y muestra en el log el resultado de carga

*/

