package com.gwj.sem4_anime_app.ui.content


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gwj.recipesapp.ui.base.BaseFragment

import com.gwj.sem4_anime_app.data.model.Comment

import com.gwj.recipesapp.ui.base.BaseViewModel
import com.gwj.sem4_anime_app.R
import com.gwj.sem4_anime_app.data.model.FavouriteAnime
import com.gwj.sem4_anime_app.data.repo.favourite.FavouriteAnimeRepo

import com.gwj.sem4_anime_app.databinding.FragmentContentBinding
import com.gwj.sem4_anime_app.ui.adapter.CommentAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentFragment : BaseFragment<FragmentContentBinding>() {
    override val viewModel: ContentViewModel by viewModels()
    private lateinit var CommentAdapter: CommentAdapter
    val args: ContentFragmentArgs by navArgs()


    private var isFavourite = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        setupCommentAdapter()
        viewModel.getAnimeDetail(args.animeId.toInt()) //get anime by id

        viewModel.getAllComments(args.animeId.toInt())

        binding.contentBackBtn.setOnClickListener {
            val action = ContentFragmentDirections.contentToHome()
            navController.navigate(action)
        }
        binding.BtnCommentTo.setOnClickListener {
            val action = ContentFragmentDirections.actionContentFragmentToCommentFragment(args.animeId)
            navController.navigate(action)
        }
    }
    private fun setupCommentAdapter() {
        CommentAdapter = CommentAdapter(emptyList())
        CommentAdapter.listener = object: CommentAdapter.Listener {
            override fun onClick(comment: Comment) {
                val action = ContentFragmentDirections.actionContentFragmentToEditCommentFragment(comment.id)
                navController.navigate(action)
            }

            override fun onDelete(comment: Comment) {
                viewModel.deleteComment(comment)
            }

        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvComments.adapter = CommentAdapter
        binding.rvComments.layoutManager = layoutManager


        binding.favoriteBtn.setOnClickListener {
            if (isFavourite) {
                viewModel.removeFavourite(args.animeId)
            } else  {
                viewModel.addFavourite(args.animeId)
            }

        }

    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {

            viewModel.comment.collect() {
                CommentAdapter.setComments(it)
            }
        }


            viewModel.favourite.collect {
                if(it != null) {
                    isFavourite = true
                    binding.favoriteBtn.setImageResource(R.drawable.ic_bookmark_favourite)
                } else {
                    isFavourite = false
                    binding.favoriteBtn.setImageResource(R.drawable.ic_bookmark_unfavourite)
                }

            }
        }



        lifecycleScope.launch {
            viewModel.anime.collect { animeDetail ->
                binding.run {
                    contentTitle.text = animeDetail?.title ?: "N/A"
                    contentTitleJP.text = animeDetail?.title_japanese ?: "N/A"
                    contentEpisodes.text = animeDetail?.episodes.toString()
                    contentYear.text = animeDetail?.aired?.string ?: "N/A"
                    contentStatus.text = animeDetail?.status ?: "N/A"
                    contentSource.text = animeDetail?.source ?: "N/A"
                    contentDuration.text = animeDetail?.duration ?: "N/A"
                    contentDesc.text = animeDetail?.synopsis ?: "No Description"

                    if (animeDetail?.trailer?.youtube_id.isNullOrEmpty()){
                        contentTrailer.visibility = View.GONE
                        contentNoTrailer.visibility = View.VISIBLE
                    } else {
                        contentTrailer.visibility = View.VISIBLE
                        contentNoTrailer.visibility = View.GONE
                        contentTrailer.setOnClickListener {
                            val action =
                                ContentFragmentDirections.actionContentFragmentToVideoFragment(
                                    animeDetail?.mal_id.toString()
                                )
                            navController.navigate(action)
                        }
                    }

                    Glide.with(binding.root)
                        .load(animeDetail?.images?.jpg?.image_url)
                        .into(contentImage)
                }
            }
        }

    }


}