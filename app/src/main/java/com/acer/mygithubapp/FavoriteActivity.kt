package com.acer.mygithubapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.acer.mygithubapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter = FavoriteAdapter(
            onFavoriteIconClick = { favorite ->
                viewModel.deleteFavorite(favorite)
            },
            onItemClick = { favorite ->
                val intent = Intent(this, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, favorite.login)
                startActivity(intent)
            }
        )

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = this@FavoriteActivity.adapter
        }

        viewModel.getAllFavorites().observe(this, { favorites ->
            if (favorites != null) {
                adapter.setList(favorites)
            }
        })
    }
}
