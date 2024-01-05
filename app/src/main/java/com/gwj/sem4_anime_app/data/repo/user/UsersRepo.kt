package com.gwj.sem4_anime_app.data.repo.user

import com.gwj.sem4_anime_app.data.model.Users

interface UsersRepo {
    suspend fun userAdd(user:Users)

    suspend fun userGet(uid:String): Users?

    suspend fun userRemove()

    suspend fun userUpdate(users: Users)
}