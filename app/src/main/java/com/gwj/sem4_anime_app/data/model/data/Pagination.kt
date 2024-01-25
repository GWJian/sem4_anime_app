package com.gwj.sem4_anime_app.data.model.data

import com.gwj.sem4_anime_app.data.model.data.Items

data class Pagination(
    val current_page: Int,
    val has_next_page: Boolean,
    val items: Items,
    val last_visible_page: Int
)