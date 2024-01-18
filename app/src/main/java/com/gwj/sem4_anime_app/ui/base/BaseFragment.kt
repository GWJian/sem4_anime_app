package com.gwj.sem4_anime_app.ui.base

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.gwj.sem4_anime_app.R
import kotlinx.coroutines.launch

abstract class BaseFragment<T: ViewBinding> :Fragment() {
    protected lateinit var navController:NavController
    protected lateinit var binding: T
    protected abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.onCreate()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        onFragmentResult()
        setupUIComponents()
        setupViewModelObserver()
    }

    protected open fun onFragmentResult() {}

    protected open fun setupViewModelObserver(){
        lifecycleScope.launch {
            viewModel.error.collect {
                showSnackbar(it, true)
            }
        }

        lifecycleScope.launch {
            viewModel.success.collect {
                showSnackbar(it)
            }
        }
    }

    protected open fun setupUIComponents() {}

    fun showSnackbar(msg: String, isError: Boolean = false) {
        val snackbar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG)
        if (isError) {
            snackbar.setBackgroundTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.error
                )
            )
        }
        snackbar.show()
    }


}