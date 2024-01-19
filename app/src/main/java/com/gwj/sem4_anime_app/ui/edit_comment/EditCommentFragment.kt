package com.gwj.sem4_anime_app.ui.edit_comment


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.gwj.sem4_anime_app.databinding.FragmentEditCommentBinding
import com.gwj.sem4_anime_app.ui.base.BaseFragment
import com.gwj.sem4_anime_app.ui.content.ContentFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditCommentFragment : BaseFragment<FragmentEditCommentBinding>() {
    override val viewModel: EditCommentViewModel by viewModels()
    val args: ContentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditCommentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun setupUIComponents() {
        super.setupUIComponents()
        viewModel.getComment(args.animeId)
        binding.btnEditComment.setOnClickListener {
            val comment = binding.tvEditComment.text.toString()
            viewModel.editComment(comment)
        }

        binding.editBackBtn.setOnClickListener {
//            val action = EditCommentFragmentDirections.editToContent(args.animeId)
//            navController.navigate(action)
            navController.popBackStack()
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