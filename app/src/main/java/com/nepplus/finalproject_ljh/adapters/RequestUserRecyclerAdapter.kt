package com.nepplus.finalproject_ljh.adapters

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nepplus.finalproject_ljh.AddFriendActivity
import com.nepplus.finalproject_ljh.R
import com.nepplus.finalproject_ljh.datas.BasicResponse
import com.nepplus.finalproject_ljh.datas.UserResponse
import com.nepplus.finalproject_ljh.web.ServerAPI
import com.nepplus.finalproject_ljh.web.ServerAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestUserRecyclerAdapter(val mContext: Context, val mList: List<UserResponse>) : RecyclerView.Adapter<RequestUserRecyclerAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.request_user_list_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.bind(mContext, mList[position])



    }

    override fun getItemCount(): Int = mList.size

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val profileImg = view.findViewById<ImageView>(R.id.profileImg)
        val nicknameTxt = view.findViewById<TextView>(R.id.nicknameTxt)
        val socialLoginImg = view.findViewById<ImageView>(R.id.socialLoginImg)
        val acceptBtn = view.findViewById<Button>(R.id.acceptBtn)
        val refuseBtn = view.findViewById<Button>(R.id.refuseBtn)

        fun bind(context: Context, data: UserResponse) {

            Glide.with(context).load(data.profileImgURL).into(profileImg)
            nicknameTxt.text = data.nickname
            when (data.provider) {
                "facebook" -> {
                    socialLoginImg.setImageResource(R.drawable.facebook_login)
                    socialLoginImg.visibility = View.VISIBLE
                }
                "kakao" -> {
                    socialLoginImg.setImageResource(R.drawable.kakao_login)
                    socialLoginImg.visibility = View.VISIBLE
                } else -> socialLoginImg.visibility = View.GONE
            }

            val sendOkOrNoToServer = object : View.OnClickListener {
                override fun onClick(p0: View?) {

                    val okOrNo = p0!!.tag.toString()

                    val retrofit = ServerAPI.getRetrofit(context)
                    val apiService = retrofit.create(ServerAPIService::class.java)
                    apiService.putRequestSendOkOrNoFriend(data.id, okOrNo).enqueue(object : Callback<BasicResponse> {
                        override fun onResponse(
                            call: Call<BasicResponse>,
                            response: Response<BasicResponse>
                        ) {

                        }

                        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                        }
                    })

                }
            }
            acceptBtn.setOnClickListener(sendOkOrNoToServer)
            refuseBtn.setOnClickListener(sendOkOrNoToServer)

        }

    }

}