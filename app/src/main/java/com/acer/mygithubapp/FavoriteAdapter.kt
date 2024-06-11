package com.acer.mygithubapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acer.mygithubapp.databinding.ItemFavoriteBinding
import com.bumptech.glide.Glide

class FavoriteAdapter(
    private val onFavoriteIconClick: (FavoriteModel) -> Unit,
    private val onItemClick: (FavoriteModel) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val list = ArrayList<FavoriteModel>()

    fun setList(favorites: List<FavoriteModel>) {
        list.clear()
        list.addAll(favorites)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoriteModel) {
            binding.apply {
                tvUsername.text = favorite.login
                Glide.with(itemView.context)
                    .load(favorite.avatarUrl)
                    .into(ivUser)

                ivFavorite.setOnClickListener {
                    onFavoriteIconClick(favorite)
                }

                itemView.setOnClickListener {
                    onItemClick(favorite)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(list[position])
    }
}
