package com.nepplus.finalproject_ljh

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nepplus.finalproject_ljh.web.ServerAPI
import com.nepplus.finalproject_ljh.web.ServerAPIService
import retrofit2.Retrofit

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context

    lateinit var retrofit: Retrofit
    lateinit var apiService: ServerAPIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        retrofit = ServerAPI.getRetrofit()
        apiService = retrofit.create(ServerAPIService::class.java)
    }

    abstract fun setupEvents()

    abstract fun setValues()

}