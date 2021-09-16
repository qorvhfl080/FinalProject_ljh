package com.nepplus.finalproject_ljh

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.nepplus.finalproject_ljh.databinding.ActivityMySettingBinding
import com.nepplus.finalproject_ljh.datas.BasicResponse
import com.nepplus.finalproject_ljh.utils.GlobalData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MySettingActivity : BaseActivity() {

    lateinit var binding: ActivityMySettingBinding

    val REQ_FOR_GALLERY = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_setting)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.profileImg.setOnClickListener {
            
            val permissionListener = object : PermissionListener {
                override fun onPermissionGranted() {

                    val myIntent = Intent()
                    myIntent.type = "image/*"
                    myIntent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(Intent.createChooser(myIntent, "프로필 사진 선택"), REQ_FOR_GALLERY)

                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(mContext, "권한 없음", Toast.LENGTH_SHORT).show()
                }
            }

            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .setDeniedMessage("설정 -> 권한에서 갤러리 권한을 열어주세요")
                .check()

        }

        binding.myPlacesLayout.setOnClickListener {
            startActivity(Intent(mContext, ViewMyPlaceListActivity::class.java))
        }

        binding.editNicknameLayout.setOnClickListener {

            val customView = LayoutInflater.from(mContext).inflate(R.layout.my_custom_alert_nickname, null)
            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("닉네임 변경")
            alert.setView(customView)
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                val nicknameEdt = customView.findViewById<EditText>(R.id.nicknameEdt)

                apiService.patchRequestMyInfo("nickname", nicknameEdt.text.toString()).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            val basicResponse = response.body()!!
                            GlobalData.loginUser = basicResponse.data.user

                            setUserInfo()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            })
            alert.setNegativeButton("취소", null)
            alert.show()
        }

        binding.readyTimeLayout.setOnClickListener {

            val customView = LayoutInflater.from(mContext).inflate(R.layout.my_custom_alert_edt, null)

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("준비 시간 설정")
            alert.setView(customView)
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                val minuteEdt = customView.findViewById<EditText>(R.id.minuteEdt)

                apiService.patchRequestMyInfo("ready_minute", minuteEdt.text.toString()).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            val basicResponse = response.body()!!

                            GlobalData.loginUser = basicResponse.data.user

                            setUserInfo()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            })
            alert.setNegativeButton("취소", null)
            alert.show()
        }

    }

    override fun setValues() {

        titleTxt.text = "내 정보 설정"

        setUserInfo()

    }

    fun setUserInfo() {
        binding.nicknameTxt.text = GlobalData.loginUser!!.nickname

        if (GlobalData.loginUser!!.readyMinute >= 60) {
            val hour = GlobalData.loginUser!!.readyMinute / 60
            val minute = GlobalData.loginUser!!.readyMinute % 60

            binding.readyTimeTxt.text = "${hour}시간 ${minute}분"
        } else {
            binding.readyTimeTxt.text = "${GlobalData.loginUser!!.readyMinute}분"
        }

        when(GlobalData.loginUser!!.provider) {
            // 이미지 파일 수정 필요
            "facebook" -> binding.socialLoginImg.setImageResource(R.drawable.default_profile)
            "kakao" -> binding.socialLoginImg.setImageResource(R.drawable.default_profile)
            else -> binding.socialLoginImg.visibility = View.GONE
        }

        when(GlobalData.loginUser!!.provider) {
            "default" -> binding.passwordLayout.visibility = View.VISIBLE
            else -> binding.passwordLayout.visibility = View.GONE
        }

        Glide.with(mContext)
            .load(GlobalData.loginUser!!.profileImgURL)
            .into(binding.profileImg)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_FOR_GALLERY) {

            if (resultCode == RESULT_OK) {

                val dataUri = data?.data

                Glide.with(mContext).load(dataUri).into(binding.profileImg)

            }

        }

    }

}
