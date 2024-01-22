package com.gwj.sem4_anime_app.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gwj.sem4_anime_app.ui.base.BaseFragment
import com.gwj.sem4_anime_app.databinding.FragmentLoginBinding
import com.gwj.sem4_anime_app.ui.register.RegisterFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()

        binding.logBtn.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPass.text.toString().trim()
            viewModel.login(email, password)
//            val action = LoginFragmentDirections.actionLoginToTabContainer()
//            navController.navigate(action)
        }

        binding.logToReg.setOnClickListener {
            val action = LoginFragmentDirections.loginToRegister()
            navController.navigate(action)


        }
        binding.forgotPass.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToResetPassFragment()
            navController.navigate(action)


        }
//        lifecycleScope.launch {
//            viewModel.user.collect {
//                val action = LoginFragmentDirections.actionLoginToTabContainer()
//                navController.navigate(action)
//
//            }
//        }



    }

    // for git push

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.success.collect {
                val action = LoginFragmentDirections.actionLoginToTabContainer()
                navController.navigate(action)
                viewModel.getCurrentUser()
            }
        }
    }
}