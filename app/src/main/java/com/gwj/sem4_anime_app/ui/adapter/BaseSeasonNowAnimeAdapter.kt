package com.gwj.sem4_anime_app.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.gwj.sem4_anime_app.data.model.Data
abstract class BaseSeasonNowAnimeAdapter(
    var seasonNowAnimes: List<Data>,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: Listener? = null

    override fun getItemCount() = seasonNowAnimes.size

    fun baseSetSeasonNowAnimes(items: List<Data>) {
        seasonNowAnimes = items
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClick(animeId: Data)
    }

}