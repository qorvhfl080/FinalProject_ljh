package com.nepplus.finalproject_ljh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        titleTxt.text = "약속 장소 확인"
        
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

            val myODsayService = ODsayService.init(mContext, "dh5CD8SqiwYKb95ygeXedLrrP9TkQ1MKp6qHe+tHc88")
            myODsayService.requestSearchPubTransPath(127.24404177611704.toString(), 37.655017048675475.toString(),
                mAppointmentData.longitude.toString(), mAppointmentData.latitude.toString(), null, null, null, object : OnResultCallbackListener {
                    override fun onSuccess(p0: ODsayData?, p1: API?) {
                        val jsonObj = p0!!.json
                        val resultObj = jsonObj.getJSONObject("result")
                        val pathArr = resultObj.getJSONArray("path")

                        val firstPath = pathArr.getJSONObject(0)
                        val infoObj = firstPath.getJSONObject("info")

                        val totalTime = infoObj.getInt("totalTime")

                        val hour = totalTime / 60
                        val minute = totalTime % 60

                        infoWindow.adapter = object : InfoWindow.DefaultViewAdapter(mContext) {
                            override fun getContentView(p0: InfoWindow): View {
                                val myView = LayoutInflater.from(mContext).inflate(R.layout.my_custom_info_window, null)

                                val placeName = myView.findViewById<TextView>(R.id.placeNameTxt)
                                val arrivalTime = myView.findViewById<TextView>(R.id.arrivalTimeTxt)

                                placeName.text = mAppointmentData.placeName

                                if (hour == 0) {
                                    arrivalTime.text = "${minute}분 소요 예정"
                                } else {
                                    arrivalTime.text = "${hour}시간 ${minute}분 소요 예정"
                                }

                                return myView
                            }
                        }

                    }
                    override fun onError(p0: Int, p1: String?, p2: API?) {

                    }
                })

//            infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(mContext) {
//                override fun getText(p0: InfoWindow): CharSequence {
//                    return "약속 장소 : ${mAppointmentData.placeName}"
//                }
//            }
            infoWindow.open(marker)

            it.setOnMapClickListener { pointF, latLng ->

                infoWindow.close()

            }

            marker.setOnClickListener {

                val clickedMarker = it as Marker

                if (clickedMarker.infoWindow == null) {
                    infoWindow.open(clickedMarker)
                } else {
                    infoWindow.close()
                }

                return@setOnClickListener true
            }

        }

    }

}