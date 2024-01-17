package com.gwj.sem4_anime_app.ui.edit_comment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.model.Comment
import com.gwj.sem4_anime_app.data.repo.comment.CommentRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCommentViewModel @Inject constructor(
    private val commentRepo: CommentRepo
) : BaseViewModel() {
    private val _comment: MutableStateFlow<Comment> = MutableStateFlow(
        Comment(comment = "")
    )
    val newComment: StateFlow<Comment> = _comment

    fun getComment(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall {
                commentRepo.getComment(id)
            }?. let {
                Log.d("testId", it.toString())
                _comment.value = it
            }
        }
    }

    fun editComment(comment: String) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall {
                commentRepo.editComment(
                    newComment.value.id,
                    newComment.value.copy(comment = comment)
                )
            }
            _success.emit("Edit Successful")
        }
    }

}