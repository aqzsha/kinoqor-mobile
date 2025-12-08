package com.example.kinoqor.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kinoqor.data.local.AuthPreferences
import com.example.kinoqor.databinding.FragmentProfileBinding
import com.example.kinoqor.ui.auth.LoginActivity
import com.example.kinoqor.ui.home.TicketsFragment
import com.example.kinoqor.ui.home.ReviewsFragment

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var prefs: AuthPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        prefs = AuthPreferences(requireContext())

        setupUI()
        return binding.root
    }

    private fun setupUI() {
        val token = prefs.getToken()

        val email = requireActivity().intent.getStringExtra("user_email") ?: "example@email.com"
        binding.tvEmailStatic.text = email

        binding.cardMyTickets.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace((requireActivity() as androidx.fragment.app.FragmentActivity).findViewById<ViewGroup>(com.example.kinoqor.R.id.container).id, TicketsFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.cardReviews.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace((requireActivity() as androidx.fragment.app.FragmentActivity).findViewById<ViewGroup>(com.example.kinoqor.R.id.container).id, ReviewsFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnLogout.setOnClickListener {
            prefs.clearToken()
            Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
