package com.example.challengepractica.ui.movie.adapter.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.challengepractica.core.BaseConcatHolder
import com.example.challengepractica.databinding.PopularMoviesRowsBinding
import com.example.challengepractica.ui.movie.adapter.MovieAdapter

//extender la clase de un adaptador de recyclerview
//recibe como tiene que verse cada una de las peliculas
//se inicializa una variable con la interfaz de click creada internamente
class PopularConcatAdapter(private val moviesAdapter: MovieAdapter) :
    RecyclerView.Adapter<BaseConcatHolder<*>>() {

    //crear interfaz para inflar los elementos de la lista que se obtuvieron
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding =
            PopularMoviesRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcatViewHolder(itemBinding)
    }

    //colocar cada elemento de la lista obtenido en "ConcatViewHolder" en cada vista
    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        //crear un holder
        when (holder) {
            is ConcatViewHolder -> holder.bind(moviesAdapter)
        }
    }

    //la vista al no ser una lista solo le va a pasar 1
    override fun getItemCount(): Int = 1

    //inner (dentro de la clase padre y su ciclo de vida para que cuando muera MovieAdapter este no quede en memoria)
    //Extender de BaseViewHolder con un objeto Movie
    //recibe la vista
    //binding.root (tener acceso a toda la vista completa)
    private inner class ConcatViewHolder(val binding: PopularMoviesRowsBinding) :
        BaseConcatHolder<MovieAdapter>(binding.root) {
        //metodo automatico bind (recibe el adaptador)
        override fun bind(adapter: MovieAdapter) {
            binding.rvPopularMovies.adapter = adapter
        }

    }
}