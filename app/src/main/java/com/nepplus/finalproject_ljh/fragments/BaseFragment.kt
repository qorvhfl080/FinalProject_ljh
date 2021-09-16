package com.nepplus.finalproject_ljh.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.nepplus.finalproject_ljh.web.ServerAPI
import com.nepplus.finalproject_ljh.web.ServerAPIService
import retrofit2.Retrofit

abstract class BaseFragment : Fragment() {

    lateinit var mContext: Context

    private lateinit var retrofit: Retrofit
    lateinit var apiService: ServerAPIService

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = requireContext()
        retrofit = ServerAPI.getRetrofit(mContext)
        apiService = retrofit.create(ServerAPIService::class.java)
    }

    abstract fun setupEvents()
    abstract fun setValues()

}