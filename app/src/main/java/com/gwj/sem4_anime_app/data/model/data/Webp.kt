package com.gwj.sem4_anime_app.data.model.data

data class Webp(
    val image_url: String,
    val large_image_url: String,
    val small_image_url: String
) {
    companion object {
        fun fromHashMap(hash: Map<String, Any>): Webp {
            return Webp(
                image_url = hash["image_url"].toString(),
                large_image_url = hash["large_image_url"].toString(),
                small_image_url = hash["small_image_url"].toString(),
            )
        }

    }
}