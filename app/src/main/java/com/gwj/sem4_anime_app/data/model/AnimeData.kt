package com.gwj.sem4_anime_app.data.model

import com.gwj.sem4_anime_app.data.model.data.Pagination

data class AnimeData(
    val `data`: List<Data>,
    val pagination: Pagination
)