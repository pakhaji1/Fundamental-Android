package com.example.githubapi

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubapi.favorite.UserFavorite
import com.example.githubapi.favorite.UserFavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(data: Application) : AndroidViewModel(data) {

    private val _userDetail = MutableLiveData<ResponseUserDetail?>()
    val userDetail: LiveData<ResponseUserDetail?> = _userDetail
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val userFavoriteRepository = UserFavoriteRepository(data)

    companion object {
        private val TAG = UserViewModel::class.java.simpleName
    }

    fun getUserDetail(login: String?){

        if (login != null) {

            _isLoading.value = true
            val client = ApiConfig.getApiService().getUserDetail(login)
            client.enqueue(object : Callback<ResponseUserDetail> {
                override fun onResponse(
                    call: Call<ResponseUserDetail>,
                    response: Response<ResponseUserDetail>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null && response.isSuccessful)
                        _userDetail.postValue(responseBody)
                    else
                        Log.e(TAG, "onResponse: ${response.message()}")
                }
                override fun onFailure(call: Call<ResponseUserDetail>, t: Throwable) {
                    Log.e(TAG, "onFailure ${t.printStackTrace()}")
                }
            })
        }
    }

    fun addUserFavorite(id: Int?, login: String?, avatar: String?) {
        if (id != null && login != null && avatar != null){
            val userFavorite = UserFavorite(id, login, avatar)
            userFavoriteRepository.insert(userFavorite)
        }
    }

    fun deleteUserFavorite(id: Int?) {
        if (id != null) userFavoriteRepository.delete(id)
    }

    fun verifyUserFavorite(id: Int) = userFavoriteRepository.verifyUserFavorite(id)
}