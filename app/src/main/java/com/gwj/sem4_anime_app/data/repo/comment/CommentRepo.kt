package com.gwj.sem4_anime_app.data.repo.comment

import com.gwj.sem4_anime_app.data.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepo {

    suspend fun getAllComments(animeId: Int): List<Comment>

    suspend fun postComment(comment: Comment)

    suspend fun getComment(id: String): Comment?

    suspend fun editComment(id: String,comment: Comment)
    suspend fun delete(id:String)
}