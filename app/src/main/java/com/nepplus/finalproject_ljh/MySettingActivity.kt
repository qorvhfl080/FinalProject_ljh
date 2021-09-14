package com.nepplus.finalproject_ljh

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.nepplus.finalproject_ljh.databinding.ActivityMySettingBinding
import com.nepplus.finalproject_ljh.utils.GlobalData

class MySettingActivity : BaseActivity() {

    lateinit var binding: ActivityMySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_setting)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.readyTimeLayout.setOnClickListener {
            
            val customView = LayoutInflater.from(mContext).inflate(R.layout.my_custom_alert_edt, null)

            val alert = AlertDialog.Builder(mContext)
            alert.setMessage("준비 시간을 입력하세요")
            alert.setTitle("준비 시간 설정")
            alert.setView(customView)
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i -> 
                
                val minuteEdt = customView.findViewById<TextView>(R.id.minuteEdt)

                Toast.makeText(mContext, "${minuteEdt.text}", Toast.LENGTH_SHORT).show()
            })
            alert.setNegativeButton("취소", null)
            alert.show()
            
        }

    }

    override fun setValues() {

        titleTxt.text = "내 정보 설정"

        binding.nicknameTxt.text = GlobalData.loginUser!!.nickname

        if (GlobalData.loginUser!!.readyMinute >= 60) {
            val hour = GlobalData.loginUser!!.readyMinute / 60
            val minute = GlobalData.loginUser!!.readyMinute % 60

            binding.readyTimeTxt.text = "${hour}시간 ${minute}분"
        } else {
            binding.readyTimeTxt.text = "${GlobalData.loginUser!!.readyMinute}분분"
        }

    }
