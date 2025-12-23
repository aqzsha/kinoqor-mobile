package com.example.kinoqor.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kinoqor.data.local.database.DatabaseProvider
import com.example.kinoqor.data.local.entity.FilmEntity
import com.example.kinoqor.data.repository.FilmRepository
import com.example.kinoqor.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var vm: MovieDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = requireArguments().getLong("id")

        val repo = FilmRepository(
            DatabaseProvider.get(requireContext()).filmDao()
        )

        vm = ViewModelProvider(
            this,
            MovieDetailsViewModelFactory(repo)
        )[MovieDetailsViewModel::class.java]

        vm.film.observe(viewLifecycleOwner) { film ->
            bindFilm(film)
        }

        vm.load(id)
    }

    private fun bindFilm(film: FilmEntity) = with(binding) {

        // Название
        tvName.text = film.name

        val meta = listOfNotNull(
            film.rate?.takeIf { it > 0 }?.let { "★ %.1f".format(it) },
            film.ageLimit?.takeIf { it > 0 }?.let { "$it+" }
        ).joinToString(" • ")

        tvMeta.text = meta
        tvMeta.isVisible = meta.isNotEmpty()

        // Описание
        tvDescription.text =
            film.description?.takeIf { it.isNotBlank() }
                ?: "Описание отсутствует"

        tvDirector.apply {
            text = film.director?.let { "Режиссёр: $it" }
            isVisible = !film.director.isNullOrBlank()
        }

        tvProduction.apply {
            text = film.production?.let { "Производство: $it" }
            isVisible = !film.production.isNullOrBlank()
        }

        tvDuration.apply {
            text = film.duration?.let { "Длительность: $it" }
            isVisible = !film.duration.isNullOrBlank()
        }

        tvPremier.apply {
            text = film.premier?.let { "Премьера: $it" }
            isVisible = !film.premier.isNullOrBlank()
        }
    }

    companion object {
        fun newInstance(id: Long) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong("id", id)
                }
            }
    }
}
