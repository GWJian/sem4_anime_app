package com.gwj.sem4_anime_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.databinding.ItemLayoutTopAnimeBinding
import com.gwj.sem4_anime_app.databinding.LayoutSearchItemBinding

class SeasonNowAnimeLinearAdapter(
    private var seasonNowAnimes: List<Data>,
) : RecyclerView.Adapter<SeasonNowAnimeLinearAdapter.SeasonNowAnimeViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonNowAnimeViewHolder {
        val binding = LayoutSearchItemBinding.inflate(
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

    inner class SeasonNowAnimeViewHolder(
        private val binding: LayoutSearchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.run {
                Glide.with(binding.root)
                    .load(data.images.jpg.image_url)
                    .into(ivAnimeImg)

                tvAnimeName.text = data.title
                tvAnimeEpisodes.text = "Episode: ${data.episodes}" //put this in String file
                tvAnimeType.text = data.type
                tvAnimeYear.text = "Year: ${data.year}"

                searchCV.setOnClickListener {
                    listener?.onClick(data)
                }
            }
        }
    }

    interface Listener {
        fun onClick(animeId: Data)
    }


}
