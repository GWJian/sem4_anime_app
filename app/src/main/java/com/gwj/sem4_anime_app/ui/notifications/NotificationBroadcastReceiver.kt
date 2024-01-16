package com.gwj.sem4_anime_app.ui.notifications

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.gwj.sem4_anime_app.MainActivity

class NotificationBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, data: Intent?) {
        // Intent for starting the main activity
        val mainActivityIntent = Intent(context, MainActivity::class.java)
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)

        // PendingIntent for the notification
        val pendingIntent = PendingIntent.getActivity(context, 0, mainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder =
            NotificationUtil.createNotificationBuilder(
                context,
                data?.getStringExtra("title") ?: "For annoying user purpose",
                data?.getStringExtra("desc") ?: "You have been not using Anime App for a while"
            )
                .setContentIntent(pendingIntent)

        NotificationUtil.notify(context, notificationBuilder.build())

        // Testing the notification working or not
        //Log.d("debugging_NotificationBroadcastReceiver", "Notification is send.")
    }
}