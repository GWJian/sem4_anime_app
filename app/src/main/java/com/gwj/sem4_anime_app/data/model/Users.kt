package com.gwj.sem4_anime_app.data.model

data class Users (
    val id: String? = null,
    val username:String,
    val email:String,
    val profilePicUrl: String? ="",
) {
    fun toHashMap(): HashMap<String, String?> {
        return hashMapOf(
            "username" to username,
            "email" to email,
            "profilePicUrl" to profilePicUrl
        )
    }

    companion object {
        fun fromHashMap(hash: Map<String, Any>): Users {
            return Users(
                id = hash["id"].toString(),
                username = hash["username"].toString(),
                email = hash["email"].toString(),
                profilePicUrl = hash["profilePicUrl"].toString()
            )
        }
    }
}

