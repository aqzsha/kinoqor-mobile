package com.example.kinoqor.ui.cinema

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kinoqor.data.local.database.DatabaseProvider
import com.example.kinoqor.data.repository.CinemaRepository
import com.example.kinoqor.databinding.FragmentCinemaDetailsBinding

class CinemaDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCinemaDetailsBinding
    private lateinit var vm: CinemaDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCinemaDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = requireArguments().getLong(ARG_ID)

        val repo = CinemaRepository(
            DatabaseProvider.get(requireContext()).cinemaDao()
        )

        vm = ViewModelProvider(
            this,
            CinemaDetailsViewModelFactory(repo)
        )[CinemaDetailsViewModel::class.java]

        vm.cinema.observe(viewLifecycleOwner) { cinema ->
            bindCinema(cinema)
        }

        vm.load(id)
    }

    private fun bindCinema(c: com.example.kinoqor.data.local.entity.CinemaEntity) {

        binding.tvName.text = c.name

        binding.tvAddress.isVisible = !c.address.isNullOrBlank()
        binding.tvAddress.text = c.address

        binding.tvDescription.isVisible = !c.description.isNullOrBlank()
        binding.tvDescription.text = c.description

        val hasCoords = c.latitude != null && c.longitude != null
        binding.tvCoordinates.isVisible = hasCoords

        if (hasCoords) {
            binding.tvCoordinates.text =
                "Координаты: ${c.latitude}, ${c.longitude}"
        }
    }

    companion object {
        private const val ARG_ID = "id"

        fun newInstance(id: Long) =
            CinemaDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, id)
                }
            }
    }
}
