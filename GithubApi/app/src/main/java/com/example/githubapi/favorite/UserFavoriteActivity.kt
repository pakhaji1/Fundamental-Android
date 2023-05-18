package com.example.githubapi.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.UserDetailActivity
import com.example.githubapi.adapter.UserFavoriteAdapter
import com.example.githubapi.databinding.ActivityUserFavoriteBinding

class UserFavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserFavoriteBinding
    private lateinit var userFavoriteViewModel: UserFavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this@UserFavoriteActivity)
        binding.rvUserFavorite.layoutManager = layoutManager

        userFavoriteViewModel = ViewModelProvider(this)[UserFavoriteViewModel::class.java]
        userFavoriteViewModel.getAllUserFavorites.observe(this) {
            setUserFavoriteData(it)
        }
    }

    private fun setUserFavoriteData(userFavorites: List<UserFavorite>) {
        val userFavoriteAdapter = UserFavoriteAdapter(userFavorites, object : UserFavoriteAdapter.OnItemClickListener {
            override fun onClick(userFavorites: UserFavorite) {
                val intent = Intent(this@UserFavoriteActivity, UserDetailActivity::class.java).apply {
                    putExtra(UserDetailActivity.EXTRA_ID, userFavorites.id)
                    putExtra(UserDetailActivity.EXTRA_LOGIN, userFavorites.login)
                    putExtra(UserDetailActivity.EXTRA_AVATAR, userFavorites.avatar)
                }
                startActivity(intent)
            }
        })
        binding.rvUserFavorite.adapter = userFavoriteAdapter
    }
}