package com.example.challengepractica.ui.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengepractica.core.BaseViewHolder
import com.example.challengepractica.data.model.Movie
import com.example.challengepractica.data.model.MovieList
import com.example.challengepractica.databinding.MovieItemBinding


//extender la clase de un adaptador de recyclerview
//recibe una lista de peliculas que lo pone en el recyclerView
//se inicializa una variable con la interfaz de click creada internamente
class MovieAdapter(
    private val movieList: List<Movie>,
    private val itemClickListener: OnMovieClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    //cuando se haga click en la pelicula devolvemos un Movie
    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    //crear interfaz para inflar los elementos de la lista que se obtuvieron
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //crear un holder
        val holder = MoviesViewHolder(itemBinding, parent.context)

        //obtener la posicion donde se esta clikeando en el holder
        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                //si no lo retorna sin posicion
                    ?: return@setOnClickListener
            //asignar la posicion del click en el metodo de la interfaz
            itemClickListener.onMovieClick((movieList[position]))
        }
        //retornamos la lista de elementos que se van a ver en la lista
        return holder
    }

    //colocar cada elemento de la lista obtenido en "onCreateViewHolder" en cada vista
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MoviesViewHolder -> holder.bind(movieList[position])
        }
    }

    //cantidad de items
    override fun getItemCount(): Int = movieList.size

    //inner (dentro de la clase padre y su ciclo de vida para que cuando muera MovieAdapter este no quede en memoria)
    //Extender de BaseViewHolder con un objeto Movie
    //recibe una vista
    //binding.root (tener acceso a toda la vista completa)
    private inner class MoviesViewHolder(
        val binding: MovieItemBinding, val context: Context
    ) : BaseViewHolder<Movie>(binding.root) {
        //metodo automatico bind (recibe la posicion de "onBindViewHolder")
        //cargar imagen de internet con Glide usando el valor de movie.poster_path
        //centerCrop (achicar la imagen al tamaño de la vista)
        //into (guardar la imagen dentro de la vista movie_item)
        override fun bind(item: Movie) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/${item.poster_path}")
                .centerCrop().into(binding.imgMovie)
        }
    }
}