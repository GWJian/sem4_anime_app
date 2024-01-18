package com.gwj.sem4_anime_app.ui.content


import androidx.lifecycle.viewModelScope
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.model.Comment
import com.gwj.sem4_anime_app.data.model.Data
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import com.gwj.sem4_anime_app.data.repo.comment.CommentRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val animeRepo: AnimeRepo,
    private val commentRepo: CommentRepo
) : BaseViewModel() {
    private val _anime: MutableStateFlow<Data?> = MutableStateFlow(null)
    val anime: StateFlow<Data?> = _anime
    private val _comment: MutableStateFlow<List<Comment>> = MutableStateFlow(emptyList())
    val comment: StateFlow<List<Comment>> = _comment
    private val _newComment: MutableStateFlow<Comment> = MutableStateFlow(
        Comment(comment = "")
    )


    fun getAnimeDetail(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall {
                animeRepo.getDetailAnime(animeId)?.let { animeDetail ->
                    _anime.value = animeDetail
                }
            }
        }
    }

    fun getAllComments(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall { commentRepo.getAllComments(animeId) }?.collect { it ->
                _comment.value = it
            }
        }
    }



    fun deleteComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            commentRepo.delete(comment.id)
        }
    }

}