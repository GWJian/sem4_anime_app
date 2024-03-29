package com.gwj.sem4_anime_app

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.gwj.sem4_anime_app.core.services.AuthService
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gwj.sem4_anime_app.core.util.NetworkManager
import com.gwj.sem4_anime_app.ui.notifications.NotificationBroadcastReceiver
import com.gwj.sem4_anime_app.ui.notifications.NotificationUtil.createNotificationChannel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    @Inject
    lateinit var authService: AuthService
    lateinit var navController: NavController

    private lateinit var dialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val backgroundColor = ResourcesCompat.getColor(resources, R.color.bgColor, null)
        window.decorView.setBackgroundColor(backgroundColor)

        navController = findNavController(R.id.navHostFragment)

        val currentUser = authService.getCurrentUser()

        if (currentUser != null) {
            navController.navigate(R.id.tabContainerFragment)
        }

        createNotificationChannel(this)

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

        window.statusBarColor = Color.BLACK
        //====================== No Connection End =====================================
    }

    //if user quite the app,alarm start ticking
    override fun onStop() {
        super.onStop()

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationBroadcastReceiver::class.java)
        intent.putExtra("title", "For annoying user purpose")
        intent.putExtra("desc","You have been not using Anime App for a while")
        val pendingIntent =
            PendingIntent.getBroadcast(this, 123, intent, PendingIntent.FLAG_MUTABLE)

        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent)
    }

    //if they back,cancel it
    override fun onResume() {
        super.onResume()

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 123, intent, PendingIntent.FLAG_MUTABLE)

        // Cancel the alarm
        alarmManager.cancel(pendingIntent)
    }


}