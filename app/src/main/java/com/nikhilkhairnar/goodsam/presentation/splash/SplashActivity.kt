package com.nikhilkhairnar.goodsam.presentation.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nikhilkhairnar.goodsam.databinding.ActivitySplashBinding
import com.nikhilkhairnar.goodsam.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeUiState()

        binding.tvError.setOnClickListener {
            startDeviceRegistration()
        }

        startDeviceRegistration()
    }

    private fun startDeviceRegistration() {
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        viewModel.registerDevice(deviceId = deviceId, modelName = Build.MODEL)
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is SplashUiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.tvError.visibility = View.GONE
                        }
                        is SplashUiState.NavigateToLogin -> {
                            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                            finish()
                        }
                        is SplashUiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvError.text = "${state.message}\nTap to retry."
                            binding.tvError.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}