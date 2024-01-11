package com.gwj.sem4_anime_app.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.databinding.ItemLayoutTopAnimeBinding

class SeasonNowAnimeAdapter(
    seasonNowAnimes: List<Data>,
) : BaseSeasonNowAnimeAdapter(seasonNowAnimes) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonNowAnimeViewHolder {
        val binding = ItemLayoutTopAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SeasonNowAnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("debugging_seasonnow", seasonNowAnimes.toString())
        val item = seasonNowAnimes[position]
        if (holder is SeasonNowAnimeViewHolder) {
            holder.bind(item)
        }
    }

    inner class SeasonNowAnimeViewHolder(
        private val binding: ItemLayoutTopAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topAnime: Data) {
            binding.run {
                Glide.with(binding.root)
                    .load(topAnime.images.jpg.image_url)
                    .into(ivAnimeImg)

                cvAnime.setOnClickListener {
                    listener?.onClick(topAnime)
                }
            }
        }
    }


}