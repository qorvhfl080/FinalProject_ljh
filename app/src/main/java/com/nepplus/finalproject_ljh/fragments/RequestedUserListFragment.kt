package com.nepplus.finalproject_ljh.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nepplus.finalproject_ljh.R
import com.nepplus.finalproject_ljh.adapters.RequestUserRecyclerAdapter
import com.nepplus.finalproject_ljh.databinding.FragmentRequestedUserListBinding
import com.nepplus.finalproject_ljh.datas.BasicResponse
import com.nepplus.finalproject_ljh.datas.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestedUserListFragment : BaseFragment() {

    lateinit var binding: FragmentRequestedUserListBinding

    val mRequestUserList = ArrayList<UserResponse>()

    lateinit var mRecyclerAdapter: RequestUserRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_requested_user_list, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()

    }

    override fun onResume() {
        super.onResume()
        getRequestUserListFromServer()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mRecyclerAdapter = RequestUserRecyclerAdapter(mContext, mRequestUserList)
        binding.requestUserRecyclerView.layoutManager = LinearLayoutManager(mContext)
        binding.requestUserRecyclerView.adapter = mRecyclerAdapter

    }

    fun getRequestUserListFromServer() {
        apiService.getRequestFriendList("requested").enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {

                    val basicResponse = response.body()!!

                    mRequestUserList.clear()
                    mRequestUserList.addAll(basicResponse.data.friends)
                    mRecyclerAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}