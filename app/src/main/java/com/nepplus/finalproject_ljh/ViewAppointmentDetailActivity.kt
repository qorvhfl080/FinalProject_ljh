package com.nepplus.finalproject_ljh

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationSource
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.MarkerIcons
import com.nepplus.finalproject_ljh.databinding.ActivityViewAppointmentDetailBinding
import com.nepplus.finalproject_ljh.datas.AppointmentData
import com.nepplus.finalproject_ljh.datas.BasicResponse
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat

class ViewAppointmentDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewAppointmentDetailBinding

    lateinit var mAppointmentData: AppointmentData

    var needLocationSendServer = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_appointment_detail)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.arrivalBtn.setOnClickListener {

            needLocationSendServer = true

            val pl = object : PermissionListener {
                override fun onPermissionGranted() {

                    if (ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return
                    }

                    val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, object : LocationListener {
                        override fun onLocationChanged(p0: Location) {

                            if (needLocationSendServer) {
                                Log.d("location", p0.latitude.toString())
                                Log.d("location", p0.longitude.toString())

                                apiService.postRequestArrival(mAppointmentData.id, p0.latitude, p0.longitude).enqueue(object : Callback<BasicResponse> {
                                    override fun onResponse(
                                        call: Call<BasicResponse>,
                                        response: Response<BasicResponse>
                                    ) {
                                        if (response.isSuccessful) {

                                            needLocationSendServer = false
                                            Toast.makeText(mContext, "약속 인증 성공", Toast.LENGTH_SHORT).show()

                                        } else {

                                            val jsonObj = JSONObject(response.errorBody()!!.string())
                                            val message = jsonObj.getString("message")
                                            Toast.makeText(mContext, "${message}", Toast.LENGTH_SHORT).show()

                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<BasicResponse>,
                                        t: Throwable
                                    ) {

                                    }
                                })
                                
                            }
                        }

                        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                        }

                        override fun onProviderEnabled(provider: String) {

                        }

                        override fun onProviderDisabled(provider: String) {

                        }
                    })

                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(mContext, "현재 위치 정보를 파악해야 약속 도착 인증이 가능합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            TedPermission.create()
                .setPermissionListener(pl)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check()

        }

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

        getAppointmentFromServer()

    }

    fun getAppointmentFromServer() {
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

            path.map = naverMap

            val centerOfStartAndDest = LatLng((mAppointmentData.startLatitude + mAppointmentData.latitude) / 2, (mAppointmentData.startLongitude + mAppointmentData.longitude))
            val cameraUpdate = CameraUpdate.scrollTo(centerOfStartAndDest)
            naverMap.moveCamera(cameraUpdate)

            val zoomLevel = 11.0
            naverMap.moveCamera(CameraUpdate.zoomTo(zoomLevel))

            val infoWindow = InfoWindow()

            val myODsayService = ODsayService.init(mContext, "dh5CD8SqiwYKb95ygeXedLrrP9TkQ1MKp6qHe+tHc88")
            myODsayService.requestSearchPubTransPath(mAppointmentData.startLongitude.toString(), mAppointmentData.startLatitude.toString(),
                mAppointmentData.longitude.toString(), mAppointmentData.latitude.toString(), null, null, null, object :
                    OnResultCallbackListener {
                    override fun onSuccess(p0: ODsayData?, p1: API?) {
                        val jsonObj = p0!!.json
                        val resultObj = jsonObj.getJSONObject("result")
                        val pathArr = resultObj.getJSONArray("path")

                        val firstPath = pathArr.getJSONObject(0)

                        val points = ArrayList<LatLng>()
                        points.add(LatLng(mAppointmentData.startLatitude, mAppointmentData.startLongitude))

                        val subPathArr = firstPath.getJSONArray("subPath")

                        for (i in 0 until subPathArr.length()) {
                            val subPathObj = subPathArr.getJSONObject(i)
                            if (!subPathObj.isNull("passStopList")) {

                                val passStopListObj = subPathObj.getJSONObject("passStopList")
                                val stationArr = passStopListObj.getJSONArray("stations")
                                for (j in 0 until stationArr.length()) {
                                    val stationObj = stationArr.getJSONObject(j)

                                    val latlng = LatLng(stationObj.getString("y").toDouble(), stationObj.getString("x").toDouble())

                                    points.add(latlng)
                                }
                            }
                        }

                        points.add(LatLng(mAppointmentData.latitude, mAppointmentData.longitude))

                        val path = PathOverlay()
                        path.coords = points
                        path.map = naverMap

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
                        infoWindow.open(marker)

                    }
                    override fun onError(p0: Int, p1: String?, p2: API?) {

                    }
                })

        }

    }

}