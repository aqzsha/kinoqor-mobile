package com.example.kinoqor.ui.cinema

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinoqor.R
import com.example.kinoqor.data.local.database.DatabaseProvider
import com.example.kinoqor.data.repository.CinemaRepository
import com.example.kinoqor.databinding.FragmentCinemasBinding
class CinemasFragment : Fragment() {

    private var _binding: FragmentCinemasBinding? = null
    private val binding get() = _binding!!

    private lateinit var vm: CinemasViewModel
    private lateinit var adapter: CinemaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCinemasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val repo = CinemaRepository(
            DatabaseProvider.get(requireContext()).cinemaDao()
        )

        vm = ViewModelProvider(
            this,
            CinemasViewModelFactory(repo)
        )[CinemasViewModel::class.java]

        adapter = CinemaAdapter { id ->
            openCinemaDetails(id)
        }

        binding.rvCinemas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCinemas.adapter = adapter

        vm.cinemas.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }

        vm.load()
    }

    private fun openCinemaDetails(id: Long) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, CinemaDetailsFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
