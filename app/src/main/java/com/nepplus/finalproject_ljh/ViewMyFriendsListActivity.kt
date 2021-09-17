package com.nepplus.finalproject_ljh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.nepplus.finalproject_ljh.adapters.FriendPagerAdapter
import com.nepplus.finalproject_ljh.databinding.ActivityViewMyFriendsListBinding
import com.nepplus.finalproject_ljh.fragments.MyFriendsListFragment
import com.nepplus.finalproject_ljh.fragments.RequestedUserListFragment

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

        binding.friendsViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                when (position) {
                    0 -> {
                        (mFPA.getItem(position) as MyFriendsListFragment).getMyFriendsListFromServer()
                    }
                    else -> {
                        (mFPA.getItem(position) as RequestedUserListFragment).getRequestUserListFromServer()
                    }
                }

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

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