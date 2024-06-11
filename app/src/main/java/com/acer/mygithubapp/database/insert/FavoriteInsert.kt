package com.acer.mygithubapp.database.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.acer.mygithubapp.FavoriteModel
import com.acer.mygithubapp.database.FavoriteRepository

class FavoriteInsert(application: Application) : ViewModel() {
    private val mNoteRepository: FavoriteRepository = FavoriteRepository(application)
    fun insert(note: FavoriteModel) {
        mNoteRepository.insert(note)
    }

    fun delete(note: FavoriteModel) {
        mNoteRepository.delete(note)
    }

    fun getFavoriteUserByUsername(login: String): LiveData<FavoriteModel> =
        mNoteRepository.getFavoriteUserByUsername(login)
}