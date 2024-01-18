package com.gwj.sem4_anime_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gwj.sem4_anime_app.data.model.FavouriteAnime
import com.gwj.sem4_anime_app.databinding.LayoutFavAnimeBinding

class FavouriteAnimeAdapter(
    private var favouriteAnimes: List<FavouriteAnime>
): RecyclerView.Adapter<FavouriteAnimeAdapter.FavouriteAnimeViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteAnimeViewHolder {
        val binding = LayoutFavAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteAnimeViewHolder(binding)
    }

    override fun getItemCount() = favouriteAnimes.size

    override fun onBindViewHolder(
        holder: FavouriteAnimeAdapter.FavouriteAnimeViewHolder,
        position: Int
    ) {
        val item = favouriteAnimes[position]
        holder.bind(item)
    }

    fun setFavouriteAnimes(items: List<FavouriteAnime>) {
        favouriteAnimes = items
        notifyDataSetChanged()
    }



    inner class FavouriteAnimeViewHolder(
        private val binding: LayoutFavAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: FavouriteAnime) {
            binding.run {

                favouriteCV.setOnClickListener {
                    listener?.onClick(anime)
                }

                Glide.with(itemView)
                    .load(anime.images.jpg.image_url)
                    .into(ivAnimeImg)

                tvAnimeName.text = "${anime.title}"

            }
        }
    }


    interface Listener {
        // Click does not send to content fragment
        fun onClick(animeId: FavouriteAnime)
    }

}





