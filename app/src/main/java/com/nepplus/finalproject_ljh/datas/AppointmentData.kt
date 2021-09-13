package com.nepplus.finalproject_ljh.datas

import com.google.gson.annotations.SerializedName

class AppointmentData(var id: Int,
                      @SerializedName("user_id")
                      var userId: Int,
                      var title: String,
                      var datetime: String,
                      @SerializedName("place")
                      var placeName: String,
                      var latitude: Double,
                      var longitude: Double,
                      @SerializedName("created_at")
                      var createdAt: String,
                      var user: UserResponse) {

    // lol

}