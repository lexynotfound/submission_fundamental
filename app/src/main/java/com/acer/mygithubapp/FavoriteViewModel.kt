package com.acer.mygithubapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.acer.mygithubapp.database.FavoriteRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites(): LiveData<List<FavoriteModel>> = repository.getAllFavorite()

    fun deleteFavorite(favorite: FavoriteModel) {
        repository.delete(favorite)
    }
}
