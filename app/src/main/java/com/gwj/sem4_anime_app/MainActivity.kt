package com.gwj.sem4_anime_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.gwj.sem4_anime_app.core.services.AuthService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    @Inject
    lateinit var authService: AuthService
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.navHostFragment)

        val currentUser = authService.getCurrentUser()

        if (currentUser != null) {
            navController.navigate(R.id.tabContainerFragment)
        }

    }


}