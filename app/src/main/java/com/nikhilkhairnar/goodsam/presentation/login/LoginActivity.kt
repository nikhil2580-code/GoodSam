package com.nikhilkhairnar.goodsam.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nikhilkhairnar.goodsam.databinding.ActivityLoginBinding
import com.nikhilkhairnar.goodsam.presentation.otp.OtpActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val mobile = binding.etMobileNumber.text.toString().trim()
            viewModel.onLoginClicked(mobile)
        }

        observeUiState()
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is LoginUiState.Idle -> {
                            binding.btnLogin.isEnabled = true
                        }
                        is LoginUiState.Loading -> {
                            binding.btnLogin.isEnabled = false
                            binding.btnLogin.text = "Please wait..."
                        }
                        is LoginUiState.NavigateToOtp -> {
                            val intent = Intent(this@LoginActivity, OtpActivity::class.java).apply {
                                putExtra(OtpActivity.EXTRA_MOBILE, state.mobile)
                                putExtra(OtpActivity.EXTRA_OTP, state.otp)
                            }
                            startActivity(intent)
                            binding.btnLogin.isEnabled = true
                            binding.btnLogin.text = "Login"
                        }
                        is LoginUiState.Error -> {
                            binding.btnLogin.isEnabled = true
                            binding.btnLogin.text = "Login"
                            Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}