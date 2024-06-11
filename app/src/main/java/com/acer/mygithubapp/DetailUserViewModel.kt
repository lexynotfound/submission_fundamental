package com.acer.mygithubapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.acer.mygithubapp.dao.FavoriteDao
import com.acer.mygithubapp.database.FavoriteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    private val _user = MutableLiveData<DetailUserResponse>()
    private val favoriteDao: FavoriteDao = FavoriteDatabase.getDatabase(application).favoriteUserDao()

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        _user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return _user
    }

    fun addFavoriteUser(user: DetailUserResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteUser = FavoriteModel(
                login = user.login,
                avatarUrl = user.avatar_url,
                isFavorite = true
            )
            favoriteDao.insert(favoriteUser)
        }
    }

    fun removeFavoriteUser(user: DetailUserResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteUser = FavoriteModel(
                login = user.login,
                avatarUrl = user.avatar_url,
                isFavorite = true
            )
            favoriteDao.delete(favoriteUser)
        }
    }

    fun isFavoriteUser(login: String): LiveData<FavoriteModel> {
        return favoriteDao.getFavoriteUserByUsername(login)
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
                return DetailUserViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
