package com.example.kinoqor.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kinoqor.data.local.AuthPreferences
import com.example.kinoqor.data.repository.AuthRepository
import com.example.kinoqor.databinding.ActivityResetPasswordBinding
import com.example.kinoqor.ui.auth.LoginActivity

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var viewModel: AuthViewModel

    private var email = ""
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = AuthPreferences(this)
        val repo = AuthRepository()
        val factory = AuthViewModelFactory(repo, prefs)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        email = intent.getStringExtra("extra_email") ?: ""
        token = intent.getStringExtra("extra_token") ?: ""

        binding.tvEmail.text = email

        binding.eye1.setOnClickListener { togglePassword(binding.etPassword) }
        binding.eye2.setOnClickListener { togglePassword(binding.etPasswordConfirm) }

        binding.btnResetPassword.setOnClickListener {
            val pass = binding.etPassword.text.toString()
            val confirm = binding.etPasswordConfirm.text.toString()
            viewModel.resetPassword(email, token, pass, confirm)
        }

        observe()
    }

    private fun observe() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is LoginUiState.Loading -> {
                    binding.btnResetPassword.text = "Processing..."
                    binding.btnResetPassword.isEnabled = false
                }

                is LoginUiState.Success -> {
                    val token = viewModel.tempToken
                    if (token.isNullOrBlank()) {
                        Toast.makeText(this, "Missing token from server", Toast.LENGTH_SHORT).show()
                        return@observe
                    }

                    val intent = Intent(this, ResetPasswordActivity::class.java)
                    intent.putExtra("extra_email", email)
                    intent.putExtra("extra_token", token)
                    startActivity(intent)
                    finish()
                }

                is LoginUiState.Error -> {
                    binding.btnResetPassword.text = "Reset password"
                    binding.btnResetPassword.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }

                is LoginUiState.Idle -> Unit
            }
        }
    }

    private fun togglePassword(edit: android.widget.EditText) {
        val isVisible = edit.inputType == android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        if (isVisible) {
            edit.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            edit.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }
        edit.setSelection(edit.text.length)
    }
}
