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
import com.example.kinoqor.data.repository.FilmRepository
import com.example.kinoqor.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: FilmAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repo = FilmRepository()
        val factory = MoviesViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[MoviesViewModel::class.java]

        adapter = FilmAdapter()
        adapter.onItemClick = { film ->
            Toast.makeText(requireContext(), "Clicked: ${film.name}", Toast.LENGTH_SHORT).show()
        }

        binding.rvFilms.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvFilms.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadFilms()
        }

        observe()
    }

    private fun observe() {
        viewModel.films.observe(viewLifecycleOwner) { list ->
            adapter.setItems(list)
        }
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            binding.swipeRefresh.isRefreshing = loading
        }
        viewModel.error.observe(viewLifecycleOwner) { err ->
            err?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
