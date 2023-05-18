package com.example.githubapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(@Query("q") value: String): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(@Path("username") username: String): Call<ResponseUserDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String): Call<List<User>>
}