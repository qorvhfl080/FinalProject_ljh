package com.nepplus.finalproject_ljh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nepplus.finalproject_ljh.adapters.MyPlaceRecyclerAdapter
import com.nepplus.finalproject_ljh.databinding.ActivityViewMyPlaceListBinding
import com.nepplus.finalproject_ljh.datas.BasicResponse
import com.nepplus.finalproject_ljh.datas.PlaceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewMyPlaceListActivity : BaseActivity() {

    lateinit var binding: ActivityViewMyPlaceListBinding

    val mMyPlaceList = ArrayList<PlaceData>()

    lateinit var mPlaceAdapter: MyPlaceRecyclerAdapter

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

        mPlaceAdapter = MyPlaceRecyclerAdapter(mContext, mMyPlaceList)
        binding.myPlaceRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        binding.myPlaceRecyclerView.adapter = mPlaceAdapter

    }

    fun getMyPlaceListFromServer() {

        apiService.getRequestMyPlaceList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val basicResponse = response.body()!!
                    mMyPlaceList.clear()
                    mMyPlaceList.addAll(basicResponse.data.places)
                    mPlaceAdapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })

    }

}