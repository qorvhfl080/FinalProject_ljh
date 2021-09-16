package com.nepplus.finalproject_ljh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nepplus.finalproject_ljh.adapters.SearchUserRecyclerAdapter
import com.nepplus.finalproject_ljh.databinding.ActivityAddFriendBinding
import com.nepplus.finalproject_ljh.datas.BasicResponse
import com.nepplus.finalproject_ljh.datas.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFriendActivity : BaseActivity() {

    lateinit var binding: ActivityAddFriendBinding

    val mSearchUserList = ArrayList<UserResponse>()

    lateinit var mSearchedUserAdapter: SearchUserRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_friend)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.searchBtn.setOnClickListener {

            val inputKeyword = binding.keywordEdt.text.toString()

            if (inputKeyword.length < 2) {
                Toast.makeText(mContext, "검색어는 2자 이상 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            apiService.getRequestSearchUser(inputKeyword).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!

                        mSearchUserList.clear()
                        mSearchUserList.addAll(basicResponse.data.users)
                        mSearchedUserAdapter.notifyDataSetChanged()

                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })

        }

    }

    override fun setValues() {

        titleTxt.text = "친구 검색/추가"

        mSearchedUserAdapter = SearchUserRecyclerAdapter(mContext, mSearchUserList)
        binding.searchUserRecyclerView.layoutManager = LinearLayoutManager(mContext)
        binding.searchUserRecyclerView.adapter = mSearchedUserAdapter

    }
}