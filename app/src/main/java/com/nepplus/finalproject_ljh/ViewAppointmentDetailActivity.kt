package com.nepplus.finalproject_ljh

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.MarkerIcons
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

        val inflater = LayoutInflater.from(mContext)
        for (friend in mAppointmentData.invitedFriendList) {

            val friendView = inflater.inflate(R.layout.invited_friends_list_item, null)

            val friendProfileImg = friendView.findViewById<ImageView>(R.id.friendProfileImg)
            val nicknameTxt = friendView.findViewById<TextView>(R.id.nicknameTxt)
            val statusTxt = friendView.findViewById<TextView>(R.id.statusTxt)

            Glide.with(mContext).load(friend.profileImgURL).into(friendProfileImg)
            nicknameTxt.text = friend.nickname

            binding.invitedFriendsLayout.addView(friendView)

        }

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
            marker.icon = MarkerIcons.BLACK
            marker.iconTintColor = Color.RED
            marker.position = dest
            marker.map = naverMap

            val startLatLng = LatLng(mAppointmentData.startLatitude, mAppointmentData.startLongitude)

            val startMarker = Marker()
            startMarker.position = startLatLng
            startMarker.map = naverMap

            val path = PathOverlay()

            val points = ArrayList<LatLng>()
            points.add(startLatLng)

            points.add(dest)

            path.coords = points
            path.map = naverMap

            val centerOfStartAndDest = LatLng((mAppointmentData.startLatitude + mAppointmentData.latitude) / 2, (mAppointmentData.startLongitude + mAppointmentData.longitude))
            val cameraUpdate = CameraUpdate.scrollTo(centerOfStartAndDest)
            naverMap.moveCamera(cameraUpdate)

            val zoomLevel = 11.0
            naverMap.moveCamera(CameraUpdate.zoomTo(zoomLevel))

        }

    }

}