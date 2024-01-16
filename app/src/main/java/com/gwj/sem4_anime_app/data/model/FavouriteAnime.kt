package com.gwj.sem4_anime_app.data.model

import android.util.Log
import com.gwj.sem4_anime_app.data.model.data.Images
import com.gwj.sem4_anime_app.data.model.data.Jpg
import com.gwj.sem4_anime_app.data.model.data.Webp

data class FavouriteAnime(
    val id: String = "",
    val mal_id: Int?,
    val title: String,
    val episodes: Int?,
    val type: String,
    val images: Images,
) {
    fun toHashMap():HashMap<String, Any?> {
        return hashMapOf(
            "id" to id,
            "mal_id" to mal_id,
            "title" to title,
            "episodes" to episodes,
            "type" to type,
            "images" to images

        )
    }

    companion object {

        fun fromHashMap(hash: Map<String, Any?>): FavouriteAnime {
            return FavouriteAnime(
                id = hash["id"].toString(),
                mal_id = hash["mal_id"].toString().toIntOrNull(),
                title = hash["title"].toString(),
                episodes = hash["episodes"].toString().toIntOrNull(),
                type = hash["type"].toString(),
                images = Images.fromHashMap(hash["images"] as Map<*, *>)
            )
        }
    }
}
