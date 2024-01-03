package com.gwj.sem4_anime_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.databinding.ItemLayoutTopAnimeBinding

class SeasonalAdapter(
    private var seasonalAnimes: List<Data>,
) : RecyclerView.Adapter<SeasonalAdapter.SeasonalAnimeViewHolder>() {

    var listener: Listener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonalAnimeViewHolder {
        val binding = ItemLayoutTopAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SeasonalAnimeViewHolder(binding)
    }

    override fun getItemCount() = seasonalAnimes.size

    override fun onBindViewHolder(holder: SeasonalAnimeViewHolder, position: Int) {
        val item = seasonalAnimes[position]
        holder.bind(item)
    }

    fun setSeasonalAnimes(items: List<Data>) {
        seasonalAnimes = items
        notifyDataSetChanged()
    }

    inner class SeasonalAnimeViewHolder(
        private val binding: ItemLayoutTopAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(seasonal: Data) {
            binding.run {
                Glide.with(binding.root)
                    .load(seasonal.images.jpg.image_url)
                    .into(ivAnimeImg)

                cvAnime.setOnClickListener {
                    listener?.onClick(seasonal)
                }

            }
        }
    }

    interface Listener {
        fun onClick(animeId: Data)
    }

}