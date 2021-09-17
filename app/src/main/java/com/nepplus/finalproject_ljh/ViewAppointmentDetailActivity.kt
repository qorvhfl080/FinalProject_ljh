package com.nepplus.finalproject_ljh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.finalproject_ljh.databinding.ActivityViewAppointmentDetailBinding

class ViewAppointmentDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewAppointmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_appointment_detail)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {

        titleTxt.text = "약속 상세 확인"

    }
}