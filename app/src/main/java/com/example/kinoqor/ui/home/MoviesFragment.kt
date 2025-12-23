package com.example.kinoqor.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kinoqor.R
import com.example.kinoqor.data.local.database.DatabaseProvider
import com.example.kinoqor.data.repository.FilmRepository
import com.example.kinoqor.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: FilmAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val repo = FilmRepository(
            DatabaseProvider.get(requireContext()).filmDao()
        )

        viewModel = ViewModelProvider(
            this,
            MoviesViewModelFactory(repo)
        )[MoviesViewModel::class.java]

        adapter = FilmAdapter { filmId ->
            openFilmDetails(filmId)
        }

        binding.rvFilms.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvFilms.adapter = adapter
        binding.rvFilms.addItemDecoration(
            GridSpacingItemDecoration(2, 24)
        )

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadFilms()
        }

        viewModel.films.observe(viewLifecycleOwner) {
            adapter.setItems(it)
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.error.observe(viewLifecycleOwner) {
            it?.let { msg ->
                binding.swipeRefresh.isRefreshing = false
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }

        binding.swipeRefresh.isRefreshing = true
        viewModel.loadFilms()
    }

    private fun openFilmDetails(filmId: Long) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                MovieDetailsFragment.newInstance(filmId)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
