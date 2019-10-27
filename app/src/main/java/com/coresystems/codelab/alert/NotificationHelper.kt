package com.coresystems.codelab.alert

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import com.coresystems.codelab.R
import com.coresystems.codelab.view.home.Home

object NotificationHelper {

    /**
     * Sets up the notification channels for API 26+.
     * Note: This uses package name + channel name to create unique channelId's.
     *
     * @param context     application context
     * @param importance  importance level for the notificaiton channel
     * @param showBadge   whether the channel should have a notification badge
     * @param name        name for the notification channel
     * @param description description for the notification channel
     */
    fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean, name: String, description: String) {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val appName = context.resources.getString(R.string.app_name)
            val channelId = "${context.packageName}-$appName"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            // Register the channel with the system
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
    /**
     * Creates a notification
     */

    fun createNotification(context: Context, title: String, description: String) {
        // create the notification
        val notificationBuilder = buildNotification(context, title, description)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1, notificationBuilder.build())
    }
    /**
     * Builds and returns the NotificationCompat.Builder notification.
     */
    private fun buildNotification(context: Context, title: String, description: String): NotificationCompat.Builder {
        val appName = context.resources.getString(R.string.app_name)
        val channelId = "${context.packageName}-${appName}"

        return NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_memo)
            setContentTitle(title)
            setContentText(description)
            setAutoCancel(true)
            color = ContextCompat.getColor(context, R.color.colorPrimary)
            setStyle(NotificationCompat.BigTextStyle()
                    .bigText(description))
            setGroup("group name")

            // Launches the app to open the reminder edit screen when tapping the whole notification
            val intent = Intent(context, Home::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            setContentIntent(pendingIntent)
        }
    }
}