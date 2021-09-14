package com.nepplus.finalproject_ljh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.nepplus.finalproject_ljh.databinding.ActivityViewMapBinding
import com.nepplus.finalproject_ljh.datas.AppointmentData

class ViewMapActivity : BaseActivity() {

    lateinit var binding: ActivityViewMapBinding

    lateinit var mAppointmentData: AppointmentData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_map)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {
        
        mAppointmentData = intent.getSerializableExtra("appointment") as AppointmentData

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.naverMapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.naverMapView, it).commit()
            }

        mapFragment.getMapAsync {

            val appointmentLatLng = LatLng(mAppointmentData.latitude, mAppointmentData.longitude)
            val cameraUpdate = CameraUpdate.scrollTo(appointmentLatLng)
            val marker = Marker()

            marker.position = appointmentLatLng
            marker.map = it
            it.moveCamera(cameraUpdate)

            val infoWindow = InfoWindow()
            infoWindow.adapter = object : InfoWindow.DefaultViewAdapter(mContext) {
                override fun getContentView(p0: InfoWindow): View {

                    val myView = LayoutInflater.from(mContext).inflate(R.layout.my_custom_info_window, null)

                    val placeName = myView.findViewById<TextView>(R.id.placeNameTxt)
                    val arrivalTime = myView.findViewById<TextView>(R.id.arrivalTimeTxt)

                    placeName.text = mAppointmentData.placeName
                    arrivalTime.text = mAppointmentData.datetime

                    return myView
                }
            }
//            infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(mContext) {
//                override fun getText(p0: InfoWindow): CharSequence {
//                    return "약속 장소 : ${mAppointmentData.placeName}"
//                }
//            }
            infoWindow.open(marker)

        }

    }

}