package com.example.mr

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FirebaseService"

    /**
     * FirebaseInstanceIdService is deprecated.
     * this is new on firebase-messaging:17.1.0
     */
    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d(TAG, "new Token: $token")
    }

    /**
     * this method will be triggered every time there is new FCM Message.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: " + remoteMessage.from)
        var message = remoteMessage.notification?.body

        if (isChatRoomOn(this)) {
            Log.d(TAG, "do Notification")
            // app is in background show notification to user
            sendNotification(message)
        } else {
            // app is forground and user see it now send broadcast to update chat
            // you can send broadcast to do anything if you want !
            Log.d(TAG, "do BroadCast")
            val intent = Intent("doChatUpdate")
            sendNotification(message)
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }

//        if(remoteMessage.notification != null) {
//            Log.d(TAG, "Notification Message Body: ${remoteMessage.notification?.body}")
//            sendNotification(remoteMessage.notification?.body)
//        }
    }

    private fun isChatRoomOn(context: Context): Boolean {
        var isChatRoomOn = true
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val taskInfo = am.getRunningTasks(1)
        val componentInfo = taskInfo[0].topActivity

        if (componentInfo!!.className == "com.example.mr.RoomActivity") {
            isChatRoomOn = false
        }

        Log.d(TAG, componentInfo.toString())

        return isChatRoomOn
    }

    private fun sendNotification(body: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("Notification", body)
        }

        createNotificationChannel(this, NotificationManagerCompat.IMPORTANCE_DEFAULT,
            false, "ntf_name", "ntf_description")

        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channelID = "$packageName-${getString(R.string.app_name)}"

        var notificationBuilder = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Push Notification FCM")
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(notificationSound)
            .setContentIntent(pendingIntent)

        var notificationManager: NotificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean,
                                          name: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "$packageName-${getString(R.string.app_name)}"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
