package com.acer.mygithubapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/repos")
    @Headers("Authorization: token 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")
    fun getRepos(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/followers")
        @Headers("Authorization: token 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")
        fun getFollowers(
            @Path("username") username: String
        ): Call<ArrayList<User>>


}