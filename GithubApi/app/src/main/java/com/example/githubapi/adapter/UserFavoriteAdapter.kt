package com.example.githubapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapi.R
import com.example.githubapi.favorite.UserFavorite
import com.example.githubapi.databinding.ItemUserBinding

class UserFavoriteAdapter (
    private val userFavorites: List<UserFavorite>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<UserFavoriteAdapter.UserFavoriteViewHolder>() {

    class UserFavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserBinding.bind(itemView)

        fun bind(userFavorites: UserFavorite) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(userFavorites.avatar)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivPhoto)
                tvUserName.text = userFavorites.login
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(userFavorites: UserFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserFavoriteViewHolder(view)
    }

    override fun getItemCount() = userFavorites.size

    override fun onBindViewHolder(holder: UserFavoriteViewHolder, position: Int) {
        val favorites = userFavorites[position]
        holder.bind(favorites)

        val fbFavorite = holder.itemView
        fbFavorite.setOnClickListener {
            onItemClickListener.onClick(favorites)
        }
//        holder.bind(userFavorites[position])
//        holder.itemView.setOnClickListener {
//            onItemClickListener.onClick(userFavorites[position])
//        }
    }


}