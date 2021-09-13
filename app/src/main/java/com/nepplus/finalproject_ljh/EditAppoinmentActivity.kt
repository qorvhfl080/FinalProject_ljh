package com.nepplus.finalproject_ljh

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.finalproject_ljh.databinding.ActivityEditAppoinmentBinding
import java.text.SimpleDateFormat
import java.util.*

class EditAppoinmentActivity : BaseActivity() {

    lateinit var binding: ActivityEditAppoinmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_appoinment)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.Btn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, h, m ->
                cal.set(Calendar.HOUR_OF_DAY, h)
                cal.set(Calendar.MINUTE, m)

                binding.textTxt.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(mContext, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }



    }

    override fun setValues() {

    }
}