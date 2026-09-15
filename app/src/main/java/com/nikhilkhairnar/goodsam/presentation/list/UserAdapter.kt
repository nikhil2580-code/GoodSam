package com.nikhilkhairnar.goodsam.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nikhilkhairnar.goodsam.databinding.ItemUserBinding
import com.nikhilkhairnar.goodsam.domain.model.User

class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvName.text = user.fullName.ifBlank { "Unknown" }
            binding.tvEmail.text = user.email ?: "-"
            binding.tvPhoneAge.text = buildString {
                append(user.phone ?: "-")
                append(" · Age ")
                append(user.age?.toString() ?: "-")
            }

            Glide.with(binding.imgUser.context)
                .load(user.imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.imgUser)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    }
}