package com.gwj.sem4_anime_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.databinding.ItemLayoutTopAnimeBinding

class VerticalAnimeAdapter(
    private var topAnimes: List<Data>,
): RecyclerView.Adapter<VerticalAnimeAdapter.TopAnimeViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopAnimeViewHolder {
        val binding = ItemLayoutTopAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TopAnimeViewHolder(binding)
    }

    override fun getItemCount() = topAnimes.size

    override fun onBindViewHolder(holder: TopAnimeViewHolder, position: Int) {
        val item = topAnimes[position]
        holder.bind(item)
    }

    fun setTopAnimes(items: List<Data>) {
        topAnimes = items
        notifyDataSetChanged()
    }

    inner class TopAnimeViewHolder(
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