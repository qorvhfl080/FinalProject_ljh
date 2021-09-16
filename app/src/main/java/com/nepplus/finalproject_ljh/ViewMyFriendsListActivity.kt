package com.nepplus.finalproject_ljh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.nepplus.finalproject_ljh.adapters.FriendPagerAdapter
import com.nepplus.finalproject_ljh.databinding.ActivityViewMyFriendsListBinding

class ViewMyFriendsListActivity : BaseActivity() {

    lateinit var binding: ActivityViewMyFriendsListBinding

    lateinit var mFPA: FriendPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_my_friends_list)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        addBtn.setOnClickListener {

            val myIntent = Intent(mContext, AddFriendActivity::class.java)
            startActivity(myIntent)

        }

    }

    override fun setValues() {

        titleTxt.text = "친구 관리"
        addBtn.visibility = View.VISIBLE

        mFPA = FriendPagerAdapter(supportFragmentManager)
        binding.friendsViewPager.adapter = mFPA

        binding.friendsTabLayout.setupWithViewPager(binding.friendsViewPager)

    }
}