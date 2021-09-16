package com.nepplus.finalproject_ljh.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nepplus.finalproject_ljh.R
import com.nepplus.finalproject_ljh.datas.UserResponse

class MyFriendsRecyclerAdapter(val mContext: Context, val mList: List<UserResponse>) : RecyclerView.Adapter<MyFriendsRecyclerAdapter.FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.friend_list_item, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {

        val data = mList[position]

        holder.bind(mContext, data)

    }

    override fun getItemCount(): Int = mList.size

    class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val friendProfileImg = view.findViewById<ImageView>(R.id.friendProfileImg)
        val nicknameTxt = view.findViewById<TextView>(R.id.nicknameTxt)
        val socialLoginImg = view.findViewById<ImageView>(R.id.socialLoginImg)

        fun bind(context: Context, data: UserResponse) {

            nicknameTxt.text = data.nickname

            Glide.with(context)
                .load(data.profileImgURL)
                .into(friendProfileImg)

            when (data.provider) {
                "facebook" -> {
                    socialLoginImg.setImageResource(R.drawable.facebook_login)
                    socialLoginImg.visibility = View.VISIBLE
                }
                "kakao" -> {
                    socialLoginImg.setImageResource(R.drawable.kakao_login)
                    socialLoginImg.visibility = View.VISIBLE
                }
                else -> {
                    socialLoginImg.visibility = View.GONE
                }
            }

        }

    }

}