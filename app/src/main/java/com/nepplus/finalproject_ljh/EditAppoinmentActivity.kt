package com.nepplus.finalproject_ljh

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.nepplus.finalproject_ljh.databinding.ActivityEditAppoinmentBinding
import com.nepplus.finalproject_ljh.datas.BasicResponse
import com.nepplus.finalproject_ljh.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import net.daum.mf.map.api.MapView as MapView

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

            if (mSelectedDateTime.get(Calendar.YEAR) == null) {
                Toast.makeText(mContext, "일자를 설정하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (mSelectedDateTime.get(Calendar.MINUTE) == null) {
                Toast.makeText(mContext, "시간을 설정하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val finalDateTime = sdf.format(mSelectedDateTime.time)
            Toast.makeText(mContext, "${finalDateTime}", Toast.LENGTH_SHORT).show()

            val inputPlaceName = binding.placeSearchEdt.text.toString()

            val lat = 37.65500913359224
            val lng = 127.24401204238616

            apiService.postRequestAppointment(inputTitle, inputDate, inputPlaceName, lat, lng)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                        val bodyResponse = response.body()

                        if (response.isSuccessful) {
                            Toast.makeText(mContext, "약속 등록 완료", Toast.LENGTH_SHORT).show()

                        }

                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })

        }

    }

    override fun setValues() {

//        카카오 지도
//        val mapView = MapView(mContext)
//        binding.mapView.addView(mapView)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.naverMapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.naverMapView, it).commit()
            }

        mapFragment.getMapAsync {
            Log.d("naverMap", it.toString())

            it.mapType = NaverMap.MapType.Hybrid

            it.minZoom = 12.0

            val neppplusCoord = LatLng(37.6550358582405, 127.24404943381579)

            val cameraUpdate = CameraUpdate.scrollTo(neppplusCoord)
            it.moveCamera(cameraUpdate)

            val uiSettings = it.uiSettings
            uiSettings.isCompassEnabled = true
            uiSettings.isScaleBarEnabled = false

        }

    }


}