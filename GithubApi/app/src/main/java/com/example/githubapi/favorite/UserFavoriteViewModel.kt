package com.example.githubapi.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.githubapi.favorite.UserFavoriteRepository

class UserFavoriteViewModel(data: Application) : AndroidViewModel(data) {
    private val userFavoriteRepository = UserFavoriteRepository(data)
    val getAllUserFavorites get() = userFavoriteRepository.getAllUserFavorite()
}