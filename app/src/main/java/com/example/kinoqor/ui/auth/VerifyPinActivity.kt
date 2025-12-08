package com.example.kinoqor.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kinoqor.data.local.AuthPreferences
import com.example.kinoqor.data.repository.AuthRepository
import com.example.kinoqor.databinding.ActivityVerifyPinBinding
import com.example.kinoqor.ui.home.HomeActivity
import android.widget.EditText
import android.widget.Toast

class VerifyPinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyPinBinding
    private lateinit var viewModel: AuthViewModel

    private val pinFields: Array<EditText?> get() = arrayOf(
        binding.et1, binding.et2, binding.et3, binding.et4, binding.et5, binding.et6
    )

    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVerifyPinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = AuthPreferences(this)
        val repository = AuthRepository()
        val factory = AuthViewModelFactory(repository, prefs)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        email = intent.getStringExtra("extra_email") ?: ""
        if (email.isNotBlank()) {
            binding.tvEmail.text = email
        }

        setupPinFields()
        setupListeners()
        observeViewModel()
    }

    private fun setupPinFields() {
        pinFields.forEachIndexed { idx, edit ->
            edit?.filters = arrayOf(InputFilter.LengthFilter(1))
            edit?.inputType = EditorInfo.TYPE_CLASS_NUMBER
            edit?.isCursorVisible = false

            edit?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (!s.isNullOrEmpty()) {
                        if (idx < pinFields.size - 1) {
                            pinFields[idx + 1]?.requestFocus()
                        } else {
                            hideKeyboard()
                            tryVerify()
                        }
                    }
                }
            })

            edit?.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_DEL &&
                    edit.text.isEmpty()
                ) {
                    if (idx > 0) {
                        pinFields[idx - 1]?.text = null
                        pinFields[idx - 1]?.requestFocus()
                    }
                    return@setOnKeyListener true
                }
                false
            }
        }
    }

    private fun setupListeners() {
        binding.tvResendAction.setOnClickListener {
            if (email.isBlank()) {
                Toast.makeText(this, "No email provided", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.forgotPassword(email)
        }

        binding.tvResend.setOnClickListener {
            binding.tvResendAction.performClick()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is LoginUiState.Idle -> {
                    setUiEnabled(true)
                }
                is LoginUiState.Loading -> {
                    setUiEnabled(false)
                }
                is LoginUiState.Success -> {
                    if (isPinFilled()) {
                        Toast.makeText(this, "Code verified", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finishAffinity()
                    } else {
                        Toast.makeText(this, "Code resent. Check your email.", Toast.LENGTH_LONG).show()
                        setUiEnabled(true)
                    }
                }
                is LoginUiState.Error -> {
                    setUiEnabled(true)
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setUiEnabled(enabled: Boolean) {
        pinFields.forEach { it?.isEnabled = enabled }
        binding.tvResendAction.isEnabled = enabled
        binding.tvResend.isEnabled = enabled
    }

    private fun isPinFilled(): Boolean {
        return pinFields.all { it?.text?.isNotEmpty() == true }
    }

    private fun tryVerify() {
        if (!isPinFilled()) return
        val pin = pinFields.joinToString(separator = "") { it?.text.toString() }
        viewModel.verifyPin(email, pin)
    }

    private fun hideKeyboard() {
        try {
            val view = currentFocus
            if (view != null) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } catch (_: Exception) { }
    }
}
