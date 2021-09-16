package com.nepplus.finalproject_ljh.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nepplus.finalproject_ljh.R
import com.nepplus.finalproject_ljh.adapters.MyFriendsRecyclerAdapter
import com.nepplus.finalproject_ljh.databinding.FragmentMyFriendsListBinding
import com.nepplus.finalproject_ljh.datas.BasicResponse
import com.nepplus.finalproject_ljh.datas.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFriendsListFragment : BaseFragment() {

    lateinit var binding: FragmentMyFriendsListBinding

    lateinit var mFriendAdapter: MyFriendsRecyclerAdapter
    val mMyFriendsList = ArrayList<UserResponse>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_friends_list, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()

    }

    override fun onResume() {
        super.onResume()
        getMyFriendsListFromServer()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mFriendAdapter = MyFriendsRecyclerAdapter(mContext, mMyFriendsList)
        binding.myFriendsRecyclerView.adapter = mFriendAdapter

    }

    fun getMyFriendsListFromServer() {

        apiService.getRequestFriendList("my").enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {

                    val basicResponse = response.body()!!

                    mMyFriendsList.clear()
                    mMyFriendsList.addAll(basicResponse.data.friends)
                    mFriendAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

}