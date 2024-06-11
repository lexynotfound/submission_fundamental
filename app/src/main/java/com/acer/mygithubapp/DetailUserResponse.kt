package com.acer.mygithubapp

data class DetailUserResponse(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val name: String,
    val bio: String,
    val public_repos: Int,
    val following: Int,
    val followers: Int
)
