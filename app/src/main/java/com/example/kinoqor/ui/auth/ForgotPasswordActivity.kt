package com.example.kinoqor.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kinoqor.data.local.AuthPreferences
import com.example.kinoqor.data.repository.AuthRepository
import com.example.kinoqor.databinding.ActivityForgotBinding
import com.example.kinoqor.ui.auth.LoginActivity

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = AuthPreferences(this)
        val repository = AuthRepository()
        val factory = AuthViewModelFactory(repository, prefs)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnSendReset.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            viewModel.forgotPassword(email)
        }

        binding.tvSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) { state ->
            when (state) {

                is LoginUiState.Idle -> {
                    binding.btnSendReset.isEnabled = true
                    binding.btnSendReset.text = "Send reset link"
                }

                is LoginUiState.Loading -> {
                    binding.btnSendReset.isEnabled = false
                    binding.btnSendReset.text = "Sending..."
                }

                is LoginUiState.Success -> {
                    Toast.makeText(this, "Reset link sent. Check your email.", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, VerifyPinActivity::class.java)
                    intent.putExtra("extra_email", binding.etEmail.text.toString())
                    startActivity(intent)
                }

                is LoginUiState.Error -> {
                    binding.btnSendReset.isEnabled = true
                    binding.btnSendReset.text = "Send reset link"
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
