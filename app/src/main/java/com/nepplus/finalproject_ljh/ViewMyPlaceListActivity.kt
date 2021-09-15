package com.nepplus.finalproject_ljh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.nepplus.finalproject_ljh.databinding.ActivityViewMyPlaceListBinding
import com.nepplus.finalproject_ljh.datas.BasicResponse
import com.nepplus.finalproject_ljh.datas.PlaceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewMyPlaceListActivity : BaseActivity() {

    lateinit var binding: ActivityViewMyPlaceListBinding

    val mMyPlaceList = ArrayList<PlaceData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_my_place_list)
        setupEvents()
        setValues()

    }

    override fun onResume() {
        super.onResume()
        getMyPlaceListFromServer()

    }

    override fun setupEvents() {

        addBtn.setOnClickListener {

            startActivity(Intent(mContext, EditMyPlaceActivity::class.java))

        }

    }

    override fun setValues() {

        titleTxt.text = "내가 자주 쓰는 출발장소들"
        addBtn.visibility = View.VISIBLE

    }

    fun getMyPlaceListFromServer() {

        apiService.getRequestMyPlaceList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val basicResponse = response.body()!!
                    mMyPlaceList.clear()
                    mMyPlaceList.addAll(basicResponse.data.places)

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })

    }

}