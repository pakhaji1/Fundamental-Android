package com.example.githubapi.favorite

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_favorite")
@Parcelize
data class UserFavorite(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "avatar")
    val avatar: String,

) : Parcelable
