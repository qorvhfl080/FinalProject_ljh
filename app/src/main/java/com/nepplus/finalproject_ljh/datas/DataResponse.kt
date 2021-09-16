package com.nepplus.finalproject_ljh.datas

import android.service.autofill.UserData
import java.io.Serializable

class DataResponse(var token: String,
                   var user: UserResponse,
                   var appointments: List<AppointmentData>,
                   var places: List<PlaceData>,
                   var friends: List<UserResponse>,
                   var users: List<UserResponse>) : Serializable {



}