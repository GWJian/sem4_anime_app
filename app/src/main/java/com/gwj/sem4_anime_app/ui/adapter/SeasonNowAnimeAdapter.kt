package com.gwj.sem4_anime_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.databinding.ItemLayoutTopAnimeBinding

class SeasonNowAnimeAdapter(
    private var seasonNowAnimes: List<Data>,
): RecyclerView.Adapter<SeasonNowAnimeAdapter.SeasonNowAnimeViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonNowAnimeViewHolder {
        val binding = ItemLayoutTopAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SeasonNowAnimeViewHolder(binding)
    }

    override fun getItemCount() = seasonNowAnimes.size

    override fun onBindViewHolder(holder: SeasonNowAnimeViewHolder, position: Int) {
        val item = seasonNowAnimes[position]
        holder.bind(item)
    }

    fun setTopAnimes(items: List<Data>) {
        seasonNowAnimes = items
        notifyDataSetChanged()
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

    interface Listener {
        fun onClick(animeId:Data)
    }

}