package com.gwj.sem4_anime_app.ui.add_comment

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gwj.sem4_anime_app.data.model.Comment
import com.gwj.sem4_anime_app.data.repo.comment.CommentRepo
import com.gwj.sem4_anime_app.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentRepo: CommentRepo
) : BaseViewModel() {


    fun postComment( animeId: String, text: String) {
        val comment = Comment(animeId = animeId, comment = text)
        Log.d("commentId", comment.copy().toHashMap().toString())
        viewModelScope.launch(Dispatchers.IO) {
            commentRepo.postComment(comment)
            _success.emit("Comment Successful")
        }
    }
}