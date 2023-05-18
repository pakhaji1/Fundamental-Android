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

class FollowsAdapter(private val listUser: List<User>): RecyclerView.Adapter<FollowsAdapter.FollowViewHolder>(){

    class FollowViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem) {
        private val binding = ItemUserBinding.bind(itemView)

        fun bind(user: User) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivPhoto)
                tvUserName.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return FollowViewHolder(view)
    }

    override fun getItemCount()= listUser.size

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
}