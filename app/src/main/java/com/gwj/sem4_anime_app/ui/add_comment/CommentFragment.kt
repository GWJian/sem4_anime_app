package com.gwj.sem4_anime_app.ui.add_comment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.sem4_anime_app.R
import com.gwj.sem4_anime_app.databinding.FragmentCommentBinding
import com.gwj.sem4_anime_app.databinding.FragmentLoginBinding
import com.gwj.sem4_anime_app.ui.content.ContentFragmentArgs
import com.gwj.sem4_anime_app.ui.content.ContentViewModel
import com.gwj.sem4_anime_app.ui.edit_comment.EditCommentFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentFragment : BaseFragment<FragmentCommentBinding>() {
    override val viewModel: CommentViewModel by viewModels()
    val args: ContentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()

        binding.btnComment.setOnClickListener {
            val comment = binding.tvComment.text.toString()
            viewModel.postComment(args.animeId, comment)
        }

        binding.contentBackBtn.setOnClickListener {
            val action = CommentFragmentDirections.editToContent(args.animeId)
            navController.navigate(action)
        }

    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.success.collect {
                navController.popBackStack()
            }
        }
    }

}