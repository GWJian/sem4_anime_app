package com.gwj.sem4_anime_app.data.model.data

data class Images(
    val jpg: Jpg,
    val webp: Webp
) {
   companion object {
       fun fromHashMap(hash: Map<String, Any>): Images {
           return Images(
               jpg = Jpg("", "", ""),
               webp = Webp("", "", "")
           )
       }

   }
}