package com.nepplus.finalproject_ljh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.finalproject_ljh.databinding.ActivityEditMyPlaceBinding

class EditMyPlaceActivity : BaseActivity() {

    lateinit var binding: ActivityEditMyPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_my_place)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {

        titleTxt.text = "내 출발장소 추가"

    }
}