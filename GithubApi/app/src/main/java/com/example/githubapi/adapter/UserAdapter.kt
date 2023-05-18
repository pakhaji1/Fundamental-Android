package com.example.githubapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapi.R
import com.example.githubapi.User
import com.example.githubapi.databinding.ItemUserBinding

class UserAdapter(private val listUser: List<User>, private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)

        fun bind(avatarUrl: String, login: String) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivPhoto)
                tvUserName.text = login
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(avatarUrl: String, login: String, id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (avatarUrl, login,id) = listUser[position]
        holder.bind(avatarUrl, login)
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(avatarUrl, login,id)
        }
    }
}