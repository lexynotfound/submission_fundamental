package com.acer.mygithubapp.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.acer.mygithubapp.FavoriteModel
import com.acer.mygithubapp.dao.FavoriteDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavDao: FavoriteDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteDatabase.getDatabase(application)
        mFavDao = db.favoriteUserDao()
    }
    fun getAllFavorite(): LiveData<List<FavoriteModel>> = mFavDao.getAllFavorite()

    fun insert(favorite: FavoriteModel) {
        executorService.execute { mFavDao.insert(favorite) }
    }
    fun delete(favorite: FavoriteModel) {
        executorService.execute { mFavDao.delete(favorite) }
    }
    fun getFavoriteUserByUsername(login: String): LiveData<FavoriteModel> = mFavDao.getFavoriteUserByUsername(login)
}