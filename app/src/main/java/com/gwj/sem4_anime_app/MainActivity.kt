package com.gwj.sem4_anime_app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gwj.sem4_anime_app.core.util.NetworkManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //====================== No Connection Start =====================================
        // Create a new MaterialAlertDialogBuilder with the specified style
        dialog = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)
            // Set the view of the dialog to be the network_dialog layout
            .setView(R.layout.network_dialog)
            // Set the dialog to be not cancelable, meaning the user can't dismiss it by clicking outside of it or pressing the back button
            .setCancelable(false)
            // Create the AlertDialog from the builder
            .create()

        val networkManager = NetworkManager(this)
        // Observe the network status. The lambda function provided will be invoked whenever the network status changes
        networkManager.observe(this) {
            // 'it' refers to the network status. If the network is not connected (!it)
            if (!it) {
                // If the dialog is not already showing, show it
                if (!dialog.isShowing) dialog.show()
            } else {
                // If the network is connected and the dialog is showing, hide it
                if (dialog.isShowing) dialog.hide()
            }
        }

        window.statusBarColor = Color.BLACK;
        //====================== No Connection End =====================================
    }
}