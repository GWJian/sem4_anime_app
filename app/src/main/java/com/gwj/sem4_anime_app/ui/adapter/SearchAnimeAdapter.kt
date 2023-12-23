package com.gwj.sem4_anime_app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.databinding.LayoutSearchItemBinding

class SearchAnimeAdapter(
    private var searchAnimes: List<Data>,
) : RecyclerView.Adapter<SearchAnimeAdapter.SearchAnimeViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAnimeViewHolder {
        val binding = LayoutSearchItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchAnimeViewHolder(binding)
    }

    override fun getItemCount() = searchAnimes.size

    override fun onBindViewHolder(holder: SearchAnimeViewHolder, position: Int) {
        val item = searchAnimes[position]
        holder.bind(item)
    }


    fun setSearchAnimes(items: List<Data>) {
        searchAnimes = items
        notifyDataSetChanged()
    }

    inner class SearchAnimeViewHolder(
        private val binding: LayoutSearchItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n") //Todo Ask sir
        fun bind(data: Data) {
            binding.run {
                Glide.with(binding.root)
                    .load(data.images.jpg.image_url)
                    .into(ivAnimeImg)

                tvAnimeName.text = data.title
                tvAnimeEpisodes.text = "Episode: ${data.episodes}"
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