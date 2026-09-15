package com.nikhilkhairnar.goodsam.presentation.otp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nikhilkhairnar.goodsam.databinding.ActivityOtpBinding
import com.nikhilkhairnar.goodsam.presentation.list.ListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtpActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOBILE = "extra_mobile"
        const val EXTRA_OTP = "extra_otp"
    }

    private lateinit var binding: ActivityOtpBinding
    private val viewModel: OtpViewModel by viewModels()

    private lateinit var mobile: String
    private var receivedOtp: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mobile = intent.getStringExtra(EXTRA_MOBILE) ?: ""
        receivedOtp = intent.getIntExtra(EXTRA_OTP, -1)

        binding.tvSentOn.text = "Sent on +91 $mobile"


        setupOtpAutoAdvance()

        binding.btnLoginOtp.setOnClickListener {
            val enteredOtp = getEnteredOtp()
            if (enteredOtp.length != 4) {
                Toast.makeText(this, "Enter the 4-digit OTP", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.onVerifyClicked(mobile = mobile, enteredOtp = enteredOtp, uniqueId = "0")
        }

        binding.tvResendOtp.setOnClickListener {
            Toast.makeText(this, "Resend not implemented in this build", Toast.LENGTH_SHORT).show()
        }

        observeUiState()
    }

    private fun getEnteredOtp(): String {
        return binding.etOtp1.text.toString() +
                binding.etOtp2.text.toString() +
                binding.etOtp3.text.toString() +
                binding.etOtp4.text.toString()
    }

    private fun setupOtpAutoAdvance() {
        val boxes = listOf(binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4)

        boxes.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1 && index < boxes.size - 1) {
                        boxes[index + 1].requestFocus()
                    }
                }
            })

            editText.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (editText.text.isEmpty() && index > 0) {
                        boxes[index - 1].apply {
                            requestFocus()
                            text?.clear()
                        }
                        return@setOnKeyListener true
                    }
                }
                false
            }
        }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is OtpUiState.Idle -> {
                            binding.btnLoginOtp.isEnabled = true
                        }
                        is OtpUiState.Loading -> {
                            binding.btnLoginOtp.isEnabled = false
                            binding.btnLoginOtp.text = "Verifying..."
                        }
                        is OtpUiState.NavigateToList -> {
                            startActivity(Intent(this@OtpActivity, ListActivity::class.java))
                            finish()
                        }
                        is OtpUiState.Error -> {
                            binding.btnLoginOtp.isEnabled = true
                            binding.btnLoginOtp.text = "Login"
                            Toast.makeText(this@OtpActivity, state.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}