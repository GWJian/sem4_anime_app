package com.gwj.sem4_anime_app.data.model.data

data class Images(
    val jpg: Jpg,
    val webp: Webp
) {
   companion object {
       fun fromHashMap(hash: Map<*, *>): Images {
           return Images(
               jpg = Jpg.fromHashMap(hash["jpg"] as Map<*, *>),
               webp = Webp.fromHashMap(hash["webp"] as Map<*, *>)
           )
       }

   }
}