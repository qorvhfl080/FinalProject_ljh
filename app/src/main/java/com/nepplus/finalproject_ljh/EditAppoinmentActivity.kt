package com.nepplus.finalproject_ljh

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.nepplus.colosseum.adapters.StartPlaceSpinnerAdapter
import com.nepplus.finalproject_ljh.databinding.ActivityEditAppoinmentBinding
import com.nepplus.finalproject_ljh.datas.BasicResponse
import com.nepplus.finalproject_ljh.datas.PlaceData
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditAppoinmentActivity : BaseActivity() {

    lateinit var binding: ActivityEditAppoinmentBinding

    val mSelectedDateTime = Calendar.getInstance()

    var mSelectedLat = 0.0
    var mSelectedLng = 0.0

    val mStartPlaceList = ArrayList<PlaceData>()
    lateinit var mSpinnerAdapter: StartPlaceSpinnerAdapter

    lateinit var mSelectedStartPlace: PlaceData

    val mStartPlaceMarker = Marker()

    val mPath = PathOverlay()

    var mNaverMap: NaverMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_appoinment)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.startPlaceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d("position", position.toString())

                mSelectedStartPlace = mStartPlaceList[position]

                mNaverMap?.let {
                    drawStartPlaceToDestination(it)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

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

            if (mSelectedLat == 0.0 && mSelectedLng == 0.0) {
                Toast.makeText(mContext, "약속장소를 지도를 클릭해서 선택해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            apiService.postRequestAppointment(inputTitle, inputDate, inputPlaceName, mSelectedLat, mSelectedLng)
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

        titleTxt.text = "약속 잡기"

        mSpinnerAdapter = StartPlaceSpinnerAdapter(mContext, R.layout.my_place_list_item, mStartPlaceList)
        binding.startPlaceSpinner.adapter = mSpinnerAdapter

        apiService.getRequestMyPlaceList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {

                    val basicResponse = response.body()!!

                    mStartPlaceList.clear()
                    mStartPlaceList.addAll(basicResponse.data.places)

                    mSpinnerAdapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })

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
            mNaverMap = it

            it.mapType = NaverMap.MapType.Hybrid

            it.minZoom = 12.0

            val neppplusCoord = LatLng(37.6550358582405, 127.24404943381579)

            val cameraUpdate = CameraUpdate.scrollTo(neppplusCoord)
            it.moveCamera(cameraUpdate)

            val uiSettings = it.uiSettings
            uiSettings.isCompassEnabled = true
            uiSettings.isScaleBarEnabled = false

            val selectedPointMaker = Marker()
            selectedPointMaker.icon = OverlayImage.fromResource(R.drawable.marker_icon_small)
            
            it.setOnMapClickListener { pointF, latLng ->
                //Toast.makeText(mContext, "위도:${latLng.latitude}, 경도:${latLng.longitude}", Toast.LENGTH_SHORT).show()

                mSelectedLat = latLng.latitude
                mSelectedLng = latLng.longitude

                selectedPointMaker.position = LatLng(mSelectedLat, mSelectedLng)
                selectedPointMaker.map = it

                drawStartPlaceToDestination(it)

            }

        }

    }

    fun drawStartPlaceToDestination(naverMap: NaverMap) {

        mStartPlaceMarker.position = LatLng(mSelectedStartPlace.latitude, mSelectedStartPlace.longitude)
        mStartPlaceMarker.map = naverMap

        val points = ArrayList<LatLng>()
        points.add(LatLng(mSelectedStartPlace.latitude, mSelectedStartPlace.longitude))

        val odsay = ODsayService.init(mContext, "dh5CD8SqiwYKb95ygeXedLrrP9TkQ1MKp6qHe+tHc88")
        odsay.requestSearchPubTransPath(mSelectedStartPlace.longitude.toString(), mSelectedStartPlace.latitude.toString(),
            mSelectedLng.toString(), mSelectedLat.toString(),
            null, null, null, object : OnResultCallbackListener {
                override fun onSuccess(p0: ODsayData?, p1: API?) {

                    val jsonObj = p0!!.json
                    val resultObj = jsonObj.getJSONObject("result")
                    val pathArr = resultObj.getJSONArray("path")
                    val firstPathObj = pathArr.getJSONObject(0)
                    val subPathArr = firstPathObj.getJSONArray("subPath")

                    for (i in 0 until subPathArr.length()) {
                        val subPathObj = subPathArr.getJSONObject(i)

                        if (!subPathObj.isNull("passStopList")) {

                            val passStopListObj = subPathObj.getJSONObject("passStopList")
                            val stationsArr = passStopListObj.getJSONArray("stations")

                            for (j in 0 until stationsArr.length()) {

                                val stationObj = stationsArr.getJSONObject(j)

                                val latLng = LatLng(stationObj.getString("y").toDouble(), stationObj.getString("x").toDouble())

                                points.add(latLng)

                            }

                        }

                    }

                    points.add(LatLng(mSelectedLat, mSelectedLng))

                    mPath.coords = points
                    mPath.map = naverMap
                }

                override fun onError(p0: Int, p1: String?, p2: API?) {

                }
            })




    }

}