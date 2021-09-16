package com.nepplus.finalproject_ljh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.finalproject_ljh.databinding.ActivityViewMyFriendsListBinding

class ViewMyFriendsListActivity : BaseActivity() {

    lateinit var binding: ActivityViewMyFriendsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_my_friends_list)

    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}