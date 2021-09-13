package com.nepplus.finalproject_ljh

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.finalproject_ljh.databinding.ActivityEditAppoinmentBinding
import java.text.SimpleDateFormat
import java.util.*

class EditAppoinmentActivity : BaseActivity() {

    lateinit var binding: ActivityEditAppoinmentBinding

    val mSelectedDateTime = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_appoinment)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.dateTxt.setOnClickListener {

            val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
//                mSelectedDateTime.set(Calendar.YEAR, i)
//                mSelectedDateTime.set(Calendar.MONTH, i2)
//                mSelectedDateTime.set(Calendar.DAY_OF_WEEK, i3)
                mSelectedDateTime.set(i, i2, i3)

                if (mSelectedDateTime.get(Calendar.MINUTE) != null)
                    binding.tempTxt.text = SimpleDateFormat("yyyy-MM-dd (E) a HH:mm").format(mSelectedDateTime.time)
                else
                    binding.tempTxt.text = SimpleDateFormat("yyyy-MM-dd").format(mSelectedDateTime.time)

            }
            DatePickerDialog(mContext, dateSetListener!!, mSelectedDateTime.get(Calendar.YEAR), mSelectedDateTime.get(Calendar.MONTH), mSelectedDateTime.get(Calendar.DAY_OF_MONTH)).show()

        }

        binding.timeTxt.setOnClickListener {

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, h, m ->
                mSelectedDateTime.set(Calendar.HOUR_OF_DAY, h)
                mSelectedDateTime.set(Calendar.MINUTE, m)

                if (mSelectedDateTime.get(Calendar.YEAR) != null)
                    binding.tempTxt.text = SimpleDateFormat("yyyy-MM-dd (E) a HH:mm").format(mSelectedDateTime.time)
                else
                    binding.tempTxt.text = SimpleDateFormat("a HH:mm").format(mSelectedDateTime.time)
            }
            TimePickerDialog(mContext, timeSetListener, mSelectedDateTime.get(Calendar.HOUR_OF_DAY), mSelectedDateTime.get(Calendar.MINUTE), false).show()

        }

        binding.okBtn.setOnClickListener {

            val inputTitle = binding.titleEdt.text.toString()
            val inputDate = ""
            val inputTime = ""
            val inputPlaceName = binding.placeSearchEdt.text.toString()

        }

    }

    override fun setValues() {

    }
}