package com.example.githubapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _listUsers = MutableLiveData<List<User>>()
    val listUsers: LiveData<List<User>> = _listUsers
    private var _listFollowers = MutableLiveData<List<User>?>()
    val listFollowers: MutableLiveData<List<User>?> = _listFollowers
    private var _listFollowing = MutableLiveData<List<User>?>()
    val listFollowing: MutableLiveData<List<User>?> = _listFollowing
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    fun getUser(value: String) {

        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(value)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (responseBody != null && response.isSuccessful) {
                    _listUsers.value = responseBody.items
                } else {
                    Log.e(TAG, "onResponse ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.e(TAG, "onFailure ${t.printStackTrace()}")
            }
        })
    }

    fun getFollowersData(login: String?) {
        if (login != null) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getFollowers(login)
            client.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null && response.isSuccessful) {
                        _listFollowers.value = responseBody
                    } else {
                        Log.e(TAG, "onResponse  ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure  ${t.printStackTrace()}")
                }
            })
        }
    }

    fun getFollowingData(login: String?) {
        if (login != null) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getFollowing(login)
            client.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null && response.isSuccessful) {
                        _listFollowing.postValue(responseBody)
                    } else {
                        Log.e(TAG, "onResponse: ${response.message()}" )
                    }
                }
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        }
    }
}