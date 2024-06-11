package com.acer.mygithubapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteModel(
    @PrimaryKey(autoGenerate = false)
    var login: String = "",
    var avatarUrl: String? = null,
    var isFavorite: Boolean = false,
)