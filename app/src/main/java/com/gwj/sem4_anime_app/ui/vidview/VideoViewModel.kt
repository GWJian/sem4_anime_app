package com.gwj.sem4_anime_app.ui.vidview

import androidx.lifecycle.ViewModel
import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.data.repo.anime.AnimeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val youtubeVideo:AnimeRepo
):BaseViewModel(){

}