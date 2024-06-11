package com.acer.mygithubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.acer.mygithubapp.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: return
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        val factory = DetailUserViewModel.Factory(application)
        viewModel = ViewModelProvider(this, factory).get(DetailUserViewModel::class.java)

        showLoading(true)
        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this, { user ->
            if (user != null) {
                binding.apply {
                    tvName.text = user.name
                    tvUsername.text = user.login
                    tvFollowers.text = "${user.followers}"
                    tvFollowing.text = "${user.following}"
                    tvBio.text = user.bio
                    tvRepo.text = "${user.public_repos}"
                    Glide.with(this@DetailUserActivity)
                        .load(user.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                    showLoading(false)
                }

                viewModel.isFavoriteUser(user.login).observe(this, { favoriteUser ->
                    isFavorite = favoriteUser != null
                    updateFabIcon()
                })

                binding.fabLove.setOnClickListener {
                    isFavorite = !isFavorite
                    if (isFavorite) {
                        viewModel.addFavoriteUser(user)
                        Snackbar.make(binding.root, "${user.login} added to favorites", Snackbar.LENGTH_SHORT).show()
                    } else {
                        viewModel.removeFavoriteUser(user)
                        Snackbar.make(binding.root, "${user.login} removed from favorites", Snackbar.LENGTH_SHORT).show()
                    }
                    updateFabIcon()
                }
            }
        })

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    private fun updateFabIcon() {
        if (isFavorite) {
            binding.fabLove.setImageResource(R.drawable.baseline_favorite_border_24) // assuming ic_favorite_red is your red heart icon
        } else {
            binding.fabLove.setImageResource(R.drawable.baseline_favorite_24) // assuming ic_favorite_border is your default heart icon
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
