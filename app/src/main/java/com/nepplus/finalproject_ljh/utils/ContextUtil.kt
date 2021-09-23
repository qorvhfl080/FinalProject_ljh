package com.nepplus.finalproject_ljh.utils

import android.content.Context

class ContextUtil() {

    companion object {

        private val prefName = "FinalProjectPref"

        val TOKEN = "TOKEN"
        val MY_READY_MINUTE = "MY_READY_MINUTE"

        fun setToken(context: Context, token: String) {

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(TOKEN, token).apply()

        }

        fun setMyReadyMinute(context: Context, minute: Int) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putInt(MY_READY_MINUTE, minute).apply()
        }

        fun getToken(context: Context): String {

            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(TOKEN, "")!!

        }

        fun getMyReadyMinute(context: Context): Int {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getInt(MY_READY_MINUTE, 0)!!
        }

    }

}