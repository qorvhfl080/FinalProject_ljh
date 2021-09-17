package com.nepplus.finalproject_ljh.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nepplus.finalproject_ljh.fragments.MyFriendsListFragment
import com.nepplus.finalproject_ljh.fragments.RequestedUserListFragment

class FriendPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> MyFriendsListFragment.getFrag()
            else -> RequestedUserListFragment.getFrag()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "내 친구 목록"
            else -> "친구 추가 요청"
        }
    }

}