package com.gwj.sem4_anime_app.ui.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gwj.sem4_anime_app.databinding.FragmentResetPassBinding
import com.gwj.sem4_anime_app.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetPassFragment : BaseFragment<FragmentResetPassBinding>() {
    override val viewModel: ResetPassViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        binding.resetPasswordBtn.setOnClickListener {
            val resetEmail = binding.etResetPassword.text.toString()
            viewModel.resetPass(resetEmail)
        }

        binding.editBackBtn.setOnClickListener {
            val action = ResetPassFragmentDirections.resetToLogin()
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