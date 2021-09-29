package com.example.challengepractica.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.challengepractica.R
import com.example.challengepractica.core.Resource
import com.example.challengepractica.data.local.AppDatabase
import com.example.challengepractica.data.local.LocalMovieDataSource
import com.example.challengepractica.data.model.Movie
import com.example.challengepractica.data.remote.RemoteMovieDataSource
import com.example.challengepractica.databinding.FragmentMovieBinding
import com.example.challengepractica.presentation.MovieViewModel
import com.example.challengepractica.presentation.MovieViewModelFactory
import com.example.challengepractica.repository.MovieRepositoryImpl
import com.example.challengepractica.repository.RetrofitClient
import com.example.challengepractica.ui.movie.adapter.MovieAdapter
import com.example.challengepractica.ui.movie.adapter.concat.PopularConcatAdapter
import com.example.challengepractica.ui.movie.adapter.concat.TopRatedConcatAdapter
import com.example.challengepractica.ui.movie.adapter.concat.UpcomingConcatAdapter

//agrego como entrada la interfaz de click de MovieAdapter
class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {


    private lateinit var binding: FragmentMovieBinding

    //crear instancia privada con delegador "by"
    //se utiliza viewModels (incluido en liberia) que solo necesita un factory
    //el factory necesita la implementacion del repositorio que se quiere usar
    //la implementacion necesita un dataSource
    //el datasource necesita un objeto o instancia del webservice
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            //inyecccion de dependencias manual
            //proveemos las dependencias a factory para generar una instancia del viewmodel
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webService),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
    )
    }

    //ConcatAdapter (en la libreria de RecyclerView)
    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //inicializar binding pasandole la vista
        binding = FragmentMovieBinding.bind(view)

        //crear instancia vacia de ConcatAdapter
        concatAdapter = ConcatAdapter()

        //Se observa y se crea una instancia de observer cada vez que se haga un "emit" en liveData
        //viewLifecycleOwner (esta al tanto de los ciclos de vida del fragmento y no permite que se dupliquen los observe)
        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                //mientras este cargando se muestra el progressBar
                is Resource.Loading -> {
                    Log.d("LiveData", "Loading...")
                    binding.progressBar.visibility = View.VISIBLE
                }
                //si terminó bien no se muestra el progressBar
                is Resource.Success -> {
                    Log.d(
                        "LiveData",
                        "Upcoming: ${result.data.first} \nTopRated: ${result.data.second} \nPopular: ${result.data.third}"
                    )
                    binding.progressBar.visibility = View.GONE

                    //mostrar las peliculas en el orden que queramos con ConcatAdapter
                    //agregamos al final un itemClickListener para poder reaccionar cuando se haga click
                    concatAdapter.apply {
                        addAdapter(0, UpcomingConcatAdapter(MovieAdapter(result.data.first.results, this@MovieFragment)))
                        addAdapter(1, TopRatedConcatAdapter(MovieAdapter(result.data.second.results, this@MovieFragment)))
                        addAdapter(2, PopularConcatAdapter(MovieAdapter(result.data.third.results, this@MovieFragment)))
                    }
                    binding.rvMovie.adapter = concatAdapter
                }
                //si terminó mal no se muestra el progressBar
                is Resource.Failure -> {
                    Log.d("LiveData", "${result.exception}")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    //pasar los datos de detalle de la pelicula seleccionada a
    // movieDetailFragment usando los arguments creados en main_graph
    override fun onMovieClick(movie: Movie) {
       Log.d("Movie", "onMovieClick: $movie")
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)
    }
}