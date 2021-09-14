package com.nepplus.finalproject_ljh.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserResponse(var id: Int,
                   var provider: String,
                   @SerializedName("nick_name")
                   var nickname: String,
                   var email: String,
                   @SerializedName("ready_minute")
                   var readyMinute: Int) : Serializable {



}