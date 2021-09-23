package com.nepplus.finalproject_ljh.receivers

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        val ALARM_ID = 1001
        val PRIMARY_CHANNEL_ID = "PRIMARY_CHANNEL_ID"
    }

    lateinit var mNotificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent?) {

        Log.d("alarm", "알람")
        mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()

    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notiChannel = NotificationChannel(PRIMARY_CHANNEL_ID, "Primary Channel", NotificationManager.IMPORTANCE_HIGH)
            notiChannel.enableLights(true)
            notiChannel.lightColor = Color.RED
            notiChannel.enableVibration(true)
            notiChannel.description = "알람을 통한 Notification 테스트"

            mNotificationManager.createNotificationChannel(notiChannel)

        }
    }

}