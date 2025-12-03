package com.example.kinoqor.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kinoqor.data.local.AuthPreferences
import com.example.kinoqor.data.repository.AuthRepository
import com.example.kinoqor.databinding.ActivityLoginBinding
import com.example.kinoqor.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = AuthPreferences(this)
        val repository = AuthRepository()
        val factory = AuthViewModelFactory(repository, prefs)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        // auto login if token exists
        viewModel.getSavedToken()?.let {
            openHome()
            return
        }

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            viewModel.login(email, password)
        }

        binding.tvForgot.setOnClickListener {
            Toast.makeText(this, "Forgot Password clicked", Toast.LENGTH_SHORT).show()
        }

        binding.tvSignupAction.setOnClickListener {
            Toast.makeText(this, "Sign Up clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is LoginUiState.Idle -> {
                    binding.btnLogin.isEnabled = true
                    binding.btnLogin.text = "Login"
                }

                is LoginUiState.Loading -> {
                    binding.btnLogin.isEnabled = false
                    binding.btnLogin.text = "Loading..."
                }

                is LoginUiState.Success -> {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    openHome()
                }

                is LoginUiState.Error -> {
                    binding.btnLogin.isEnabled = true
                    binding.btnLogin.text = "Login"
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}