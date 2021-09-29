package com.example.challengepractica.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.challengepractica.R
import com.example.challengepractica.databinding.FragmentMovieDetailBinding


class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var binding: FragmentMovieDetailBinding
    //pasarle a layaout los argumentos a traves de navArgs
    //la clase "MovieDetailFragmentArgs" se genera luego de
    //cargar los argumentos en el navigation y hacer RebuildProject
    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)

        //cargar imagen de internet con Glide usando el valor de poster_path y background
        //centerCrop (achicar la imagen al tamaño de la vista)
        //into (guardar la imagen dentro de la vista imgMovie)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.posterImageUrl}").centerCrop().into(binding.imgMovie)
        Glide.with(requireContext()).load("https://image.tmdb.org/t/p/w500/${args.backgroundImageUrl}").centerCrop().into(binding.imgBackground)

        //cargar el resto de los argumentos
        binding.txtDescription.text = args.overview
        binding.txtMovieTitle.text = args.tittle
        binding.txtLanguage.text = "Language: ${args.language}"
        binding.txtRating.text = "${args.voteAverage} (${args.voteCount} Reviews)"
        binding.txtReleased.text = "Released ${args.releaseDate}"
    }
}