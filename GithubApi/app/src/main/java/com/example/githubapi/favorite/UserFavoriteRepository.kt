package com.example.githubapi.favorite

import android.app.Application
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserFavoriteRepository(data: Application) {

    private val userFavoriteDao: UserFavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val database = UserRoomDatabase.getDatabase(data)
        userFavoriteDao = database.userFavoriteDao()
    }

    fun insert(userFavorite: UserFavorite) {
        executorService.execute { userFavoriteDao.insert(userFavorite) }
    }

    fun delete(id: Int): Int {
        val delete = executorService.submit(Callable { userFavoriteDao.delete(id)})
        return delete.get()
    }

    fun verifyUserFavorite(id: Int): Int {
        val verifyUserFavorite = executorService.submit(Callable { userFavoriteDao.verifyUserFavorite(id)})
        return verifyUserFavorite.get()
    }

    fun getAllUserFavorite() = userFavoriteDao.getAllUserFavorite()

}