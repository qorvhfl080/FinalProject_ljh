package com.nepplus.finalproject_ljh.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class AppointmentData(var id: Int,
                      @SerializedName("user_id")
                      var userId: Int,
                      var title: String,
                      var datetime: Date,
                      @SerializedName("place")
                      var placeName: String,
                      var latitude: Double,
                      var longitude: Double,
                      @SerializedName("created_at")
                      var createdAt: String,
                      var user: UserResponse) : Serializable {

    fun getFormattedDateTime(): String {

        val now = Calendar.getInstance()

        val diff = this.datetime.time - now.timeInMillis

        val diffHour = diff / 1000 / 60 / 60

        if (diffHour < 1) {
            val diffMinute = diff / 1000 / 60
            return "${diffMinute}분 남음"
        } else if (diffHour <= 5) {
            return "${diffHour}시간 남음"
        } else {
            val sdf = SimpleDateFormat("M/d a h:mm")
            return sdf.format(this.datetime)
        }

    }

}