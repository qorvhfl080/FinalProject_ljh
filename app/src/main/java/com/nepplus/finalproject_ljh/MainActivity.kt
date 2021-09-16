package com.nepplus.finalproject_ljh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nepplus.colosseum.adapters.AppointmentAdapter
import com.nepplus.finalproject_ljh.adapters.AppointmentRecyclerAdapter
import com.nepplus.finalproject_ljh.databinding.ActivityMainBinding
import com.nepplus.finalproject_ljh.datas.AppointmentData
import com.nepplus.finalproject_ljh.datas.BasicResponse
import com.nepplus.finalproject_ljh.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    //lateinit var mAppointmentAdapter: AppointmentAdapter
    lateinit var mRecyclerAdapter: AppointmentRecyclerAdapter

    val mAppointmentList = ArrayList<AppointmentData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()

    }

    override fun onResume() {
        super.onResume()

        getAppointmentListFromServer()

    }

    override fun setupEvents() {

        binding.addAppoinmentBtn.setOnClickListener {

            val myIntent = Intent(mContext, EditAppoinmentActivity::class.java)
            startActivity(myIntent)

        }

        profileImg.setOnClickListener {
            startActivity(Intent(mContext, MySettingActivity::class.java))
        }

    }

    override fun setValues() {

        Toast.makeText(mContext, "${GlobalData.loginUser?.nickname}님 환영합니다.", Toast.LENGTH_SHORT).show()

//        mAppointmentAdapter = AppointmentAdapter(mContext, R.layout.appointment_list_item, mAppointmentList)
//        binding.appointmentListView.adapter = mAppointmentAdapter
        mRecyclerAdapter = AppointmentRecyclerAdapter(mContext, mAppointmentList)
        binding.appointmentRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        binding.appointmentRecyclerView.adapter = mRecyclerAdapter

        profileImg.visibility = View.VISIBLE
        titleTxt.text = "메인 화면"

    }

    fun getAppointmentListFromServer() {

        apiService.getRequestAppointmentList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                val basicResponse = response.body()!!

                mAppointmentList.clear()

                mAppointmentList.addAll(basicResponse.data.appointments)

                mRecyclerAdapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })

    }
}