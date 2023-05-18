package com.example.githubapi.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavoriteDao {
    @Insert
    fun insert(userFavorite: UserFavorite)

    @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
    fun delete(id: Int): Int

    @Query("SELECT * FROM user_favorite WHERE user_favorite.id = :id")
    fun verifyUserFavorite(id: Int): Int

    @Query("SELECT * FROM user_favorite ORDER BY id DESC")
    fun getAllUserFavorite(): LiveData<List<UserFavorite>>

}