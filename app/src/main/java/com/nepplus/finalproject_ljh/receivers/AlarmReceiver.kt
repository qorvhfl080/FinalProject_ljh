package com.nepplus.finalproject_ljh.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        val ALARM_ID = 1001
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("alarm", "알람")

    }

}