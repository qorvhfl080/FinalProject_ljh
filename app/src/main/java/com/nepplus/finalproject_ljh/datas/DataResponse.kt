package com.nepplus.finalproject_ljh.datas

import java.io.Serializable

class DataResponse(var token: String,
                   var user: UserResponse,
                   var appointments: List<AppointmentData>,
                   var places: List<PlaceData>) : Serializable {



}