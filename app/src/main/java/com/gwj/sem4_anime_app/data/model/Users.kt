package com.gwj.sem4_anime_app.data.model

data class Users (
    val id: String? = null,
    val username:String,
    val useremail:String,
    val userimg:String? ="",
) {
    fun toHashMap(): HashMap<String, String?> {
        return hashMapOf(
            "username" to username,
            "useremail" to useremail,
            "userimg" to userimg
        )
    }

    companion object {
        fun fromHashMap(hash: Map<String, Any>): Users {
            return Users(
                id = hash["id"].toString(),
                username = hash["username"].toString(),
                useremail = hash["useremail"].toString(),
                userimg = hash["userimg"].toString()
            )
        }
    }
}

