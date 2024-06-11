package com.acer.mygithubapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.acer.mygithubapp.FavoriteModel


@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fav: FavoriteModel)

    @Delete
    fun delete(fav: FavoriteModel)

    @Query("SELECT * from FavoriteModel ORDER BY login ASC")
    fun getAllFavorite(): LiveData<List<FavoriteModel>>

    @Query("SELECT * FROM FavoriteModel WHERE login = :login")
    fun getFavoriteUserByUsername(login: String): LiveData<FavoriteModel>

}
