package com.nepplus.finalproject_ljh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import com.nepplus.finalproject_ljh.databinding.ActivityViewAppointmentDetailBinding
import com.nepplus.finalproject_ljh.datas.AppointmentData
import java.text.SimpleDateFormat

class ViewAppointmentDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewAppointmentDetailBinding

    lateinit var mAppointmentData: AppointmentData

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

        mAppointmentData = intent.getSerializableExtra("appointment") as AppointmentData

        binding.titleTxt.text = mAppointmentData.title
        binding.placeTxt.text = mAppointmentData.placeName
        binding.invitedFriendsCountTxt.text = "(참여인원 : ${mAppointmentData.invitedFriendList}명)"
        val sdf = SimpleDateFormat("M/d a h:mm")
        binding.titleTxt.text = sdf.format(mAppointmentData.datetime)

        setNaverMap()

    }

    fun setNaverMap() {

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.naverMapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.naverMapView, it).commit()
            }

        mapFragment.getMapAsync {
            val naverMap = it
            val marker = Marker()
            val dest = LatLng(mAppointmentData.latitude, mAppointmentData.longitude)
            marker.position = dest
            marker.map = naverMap

            val cameraUpdate = CameraUpdate.scrollTo(dest)
            naverMap.moveCamera(cameraUpdate)

        }

    }

}