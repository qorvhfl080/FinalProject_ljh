package com.nepplus.finalproject_ljh.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import com.nepplus.finalproject_ljh.receivers.AlarmReceiver
import java.util.*

class MyJobService : JobService() {

    companion object {
        val JOB_TIME_SET = 1000
    }

    override fun onStartJob(p0: JobParameters?): Boolean {

        Log.d("JobService", p0?.jobId.toString())

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val myIntent = Intent(this, AlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(this, AlarmReceiver.ALARM_ID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val now = Calendar.getInstance()
        val triggerTime = now.timeInMillis + 60 * 1000

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)



        return false
    }

    override fun onStopJob(p0: JobParameters?): Boolean {

        return false
    }
}