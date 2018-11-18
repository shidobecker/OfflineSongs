package com.coderoom.offlinesongs.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.coderoom.offlinesongs.R


open class NotificationCreator(val context: Context) : ContextWrapper(context) {

    companion object {
        const val UPDATE_NOTIFY_ID = 5723
        const val SYNC_NOTIFY_ID = 5660
        const val ALERT_OFFLINE_NOTIFY_ID = 8282
    }


    fun createSyncNotification(title: String, body: String): Notification {
        val channelId =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createSyncChannel()
                } else {
                    ""
                }

        return NotificationCompat.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_PROGRESS)
                .setShowWhen(true)
                .setLocalOnly(true)
                .build()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun createSyncChannel(): String {
        val channelId = context.getString(R.string.channel_id)
        val channelName = context.getString(R.string.channel_name)
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)

        channel.enableLights(true)
        channel.enableVibration(true)

        channel.lightColor = Color.RED
        channel.importance = NotificationManager.IMPORTANCE_HIGH
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        getManager().createNotificationChannel(channel)
        return channelId
    }



    private fun getManager(): NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun notify(id: Int, notification: Notification) {
        getManager().notify(id, notification)
    }


}