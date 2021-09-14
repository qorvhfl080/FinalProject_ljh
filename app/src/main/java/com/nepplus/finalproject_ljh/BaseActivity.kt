package com.nepplus.finalproject_ljh

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.nepplus.finalproject_ljh.web.ServerAPI
import com.nepplus.finalproject_ljh.web.ServerAPIService
import retrofit2.Retrofit

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context

    lateinit var retrofit: Retrofit
    lateinit var apiService: ServerAPIService

    lateinit var profileImg: ImageView
    lateinit var titleTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        retrofit = ServerAPI.getRetrofit(mContext)
        apiService = retrofit.create(ServerAPIService::class.java)

        supportActionBar?.let {
            setCustomActionBar()
        }

    }

    abstract fun setupEvents()

    abstract fun setValues()

    fun setCustomActionBar() {

        val defActionBar = supportActionBar!!

        defActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defActionBar.setCustomView(R.layout.my_custom_action_bar)

        val toolbar = defActionBar.customView.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0, 0)

        profileImg = defActionBar.customView.findViewById(R.id.profileImg)
        titleTxt = defActionBar.customView.findViewById(R.id.titleTxt)

    }

}