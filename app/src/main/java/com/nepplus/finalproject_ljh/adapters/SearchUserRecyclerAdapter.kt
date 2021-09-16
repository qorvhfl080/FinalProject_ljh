package com.nepplus.finalproject_ljh.adapters

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nepplus.finalproject_ljh.R
import com.nepplus.finalproject_ljh.datas.UserResponse

class SearchUserRecyclerAdapter(val mContext: Context, val mList: List<UserResponse>) : RecyclerView.Adapter<SearchUserRecyclerAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.search_user_list_item, parent, false)
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
        val addFriendBtn = view.findViewById<Button>(R.id.addFriendBtn)

        fun bind(context: Context, data: UserResponse) {
            Glide.with(context)
                .load(data.profileImgURL)
                .into(profileImg)
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

            addFriendBtn.setOnClickListener {

                val alert = AlertDialog.Builder(context)
                alert.setMessage("${data.nickname}님을 친구로 추가하겠습니까?")
                alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

                })
                alert.setNegativeButton("취소", null)
                alert.show()
            }

        }

    }

}