package com.nikhilkhairnar.goodsam.presentation.list

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikhilkhairnar.goodsam.databinding.ActivityListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private val viewModel: ListViewModel by viewModels()
    private val adapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListActivity)
            adapter = this@ListActivity.adapter
        }

        binding.tvRetry.setOnClickListener {
            viewModel.loadUsers()
        }

        observeUiState()
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is ListUiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.errorContainer.visibility = View.GONE
                        }
                        is ListUiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.errorContainer.visibility = View.GONE
                            adapter.submitList(state.users)
                        }
                        is ListUiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.GONE
                            binding.errorContainer.visibility = View.VISIBLE
                            binding.tvErrorMessage.text = state.message
                        }
                    }
                }
            }
        }
    }
}