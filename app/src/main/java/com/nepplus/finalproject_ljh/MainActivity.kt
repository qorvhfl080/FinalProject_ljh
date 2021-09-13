package com.nepplus.finalproject_ljh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.colosseum.adapters.AppointmentAdapter
import com.nepplus.finalproject_ljh.databinding.ActivityMainBinding
import com.nepplus.finalproject_ljh.datas.AppointmentData
import com.nepplus.finalproject_ljh.datas.BasicResponse
import com.nepplus.finalproject_ljh.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var mAppointmentAdapter: AppointmentAdapter

    val mAppointmentList = ArrayList<AppointmentData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.addAppoinmentBtn.setOnClickListener {

            val myIntent = Intent(mContext, EditAppoinmentActivity::class.java)
            startActivity(myIntent)

        }

    }

    override fun setValues() {

        Toast.makeText(mContext, "${GlobalData.loginUser?.nickname}님 환영합니다.", Toast.LENGTH_SHORT).show()

        getAppointmentListFromServer()

    }

    fun getAppointmentListFromServer() {

        apiService.getRequestAppointmentList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                val basicResponse = response.body()!!

                mAppointmentList.addAll(basicResponse.data.appointments)

                mAppointmentAdapter.

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })

    }
}