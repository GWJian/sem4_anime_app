package com.gwj.sem4_anime_app.data.model.data

import android.util.Log

data class Jpg(
    val image_url: String,
    val large_image_url: String,
    val small_image_url: String
) {
    companion object {
        fun fromHashMap(hash: Map<*, *>): Jpg {
            Log.d("debugging", hash["jpg"].toString())
            return Jpg(
                image_url = hash["image_url"].toString(),
                large_image_url = hash["large_image_url"].toString(),
                small_image_url = hash["small_image_url"].toString(),
            )
        }

    }
}