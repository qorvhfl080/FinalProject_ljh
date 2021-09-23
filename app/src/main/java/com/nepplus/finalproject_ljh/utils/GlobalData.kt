package com.nepplus.finalproject_ljh.utils

import android.content.Context
import android.util.Log
import com.nepplus.finalproject_ljh.datas.UserResponse

class GlobalData {

    companion object {

        var context: Context? = null

        var loginUser: UserResponse? = null

        set(value) {

            value?.let {
                ContextUtil.setMyReadyMinute(context!!, it.readyMinute)
            }

            if (value == null) {
                ContextUtil.setMyReadyMinute(context!!, 0)
            }

            field = value
        }

    }

}