package com.nepplus.finalproject_ljh.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import com.nepplus.finalproject_ljh.datas.BasicResponse
import com.nepplus.finalproject_ljh.receivers.AlarmReceiver
import com.nepplus.finalproject_ljh.utils.GlobalData
import com.nepplus.finalproject_ljh.web.ServerAPI
import com.nepplus.finalproject_ljh.web.ServerAPIService
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MyJobService : JobService() {

    companion object {
        val JOB_TIME_SET = 1000
    }

    override fun onStartJob(p0: JobParameters?): Boolean {

        Log.d("JobService", p0?.jobId.toString())

        val retrofit = ServerAPI.getRetrofit(applicationContext)
        val apiService = retrofit.create(ServerAPIService::class.java)

        apiService.getRequestAppointmentDetail(p0!!.jobId).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {

                    val basicResponse = response.body()!!
                    Log.d("jobService", basicResponse.data.appointment.toString())

                    val appointmentData = basicResponse.data.appointment

                    val myODsayService = ODsayService.init(applicationContext, "")
                    myODsayService.requestSearchPubTransPath(appointmentData.startLongitude.toString(), appointmentData.startLatitude.toString(),
                        appointmentData.longitude.toString(), appointmentData.latitude.toString(),
                        null, null, null, object : OnResultCallbackListener {
                            override fun onSuccess(p0: ODsayData?, p1: API?) {

                                val jsonObj = p0!!.json
                                val resultObj = jsonObj.getJSONObject("result")
                                val pathArr = resultObj.getJSONArray("path")

                                val firstPath = pathArr.getJSONObject(0)

                                val infoObj = firstPath.getJSONObject("info")

                                val totalTime = infoObj.getInt("totalTime")

                                val hour = totalTime / 60
                                val minute = totalTime % 60

                                val now = Calendar.getInstance()
                                appointmentData.datetime.time += now.timeZone.rawOffset
                                val alarmTime = appointmentData.datetime.time - totalTime*60*1000 - GlobalData.loginUser!!.readyMinute*60*1000
                                setAlarmByMinute(alarmTime)

                            }

                            override fun onError(p0: Int, p1: String?, p2: API?) {

                            }
                        })



                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })


        setAlarmByMinute()


        return false
    }

    fun setAlarmByMinute(timeInMillis: Long) {

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val myIntent = Intent(this, AlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(this, AlarmReceiver.ALARM_ID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val triggerTime = timeInMillis

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)

    }

    override fun onStopJob(p0: JobParameters?): Boolean {

        return false
    }
}