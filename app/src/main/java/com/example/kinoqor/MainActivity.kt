package com.example.kinoqor.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kinoqor.R
import com.example.kinoqor.databinding.ActivityMainBinding
import com.example.kinoqor.ui.cinema.CinemasFragment
import com.example.kinoqor.ui.home.MoviesFragment
import com.example.kinoqor.ui.home.ProfileFragment
import com.example.kinoqor.ui.home.TicketsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val moviesFragment = MoviesFragment()
    private val cinemasFragment = CinemasFragment()
    private val ticketsFragment = TicketsFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, moviesFragment)
            .commit()

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_movies -> showFragment(moviesFragment)
                R.id.navigation_cinemas -> showFragment(cinemasFragment)
                R.id.navigation_tickets -> showFragment(ticketsFragment)
                R.id.navigation_profile -> showFragment(profileFragment)
            }
            true
        }
    }

    private fun showFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
