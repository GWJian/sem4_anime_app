package com.gwj.sem4_anime_app.data.model

data class Comment(
    val id: String = "",
    val animeId: String = "",
    val addedBy: String = "",
    val addedOn: String = "",
    val comment: String
) {
    fun toHashMap():HashMap<String, Any>
    {
        return hashMapOf(
            "id" to id,
            "animeId" to animeId,
            "addedBy" to addedBy,
            "addedOn" to addedOn,
            "comment" to comment,
        )
    }
    companion object
    {
        fun fromHashMap(hash: Map<String, Any>): Comment
        {
            return Comment(
                id = hash["id"].toString(),
                animeId = hash["animeId"].toString(),
                addedBy = hash["addedBy"].toString(),
                addedOn = hash["addedOn"].toString(),
                comment = hash["comment"].toString()
            )
        }
    }
}
