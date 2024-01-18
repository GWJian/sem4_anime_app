package com.gwj.sem4_anime_app.ui.register


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gwj.recipesapp.ui.base.BaseFragment
import com.gwj.sem4_anime_app.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment  : BaseFragment<FragmentRegisterBinding>() {

    override val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()

        binding.run {
            registerBtn.setOnClickListener {
                viewModel.signUp(
                    registerUsername.text.toString(),
                    registerEmail.text.toString(),
                    registerPass.text.toString(),
                    confirmPass.text.toString()
                )
            }


//            registerToLogin.setOnClickListener {
//                navController.popBackStack()
//            }

        }
        binding.registerToLogin.setOnClickListener {
            val action = RegisterFragmentDirections.registerToLogin()
            navController.navigate(action)
        }
    }


    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.success.collect {
                val action = RegisterFragmentDirections.registerToLogin()
                navController.navigate(action)
            }
        }
    }

}