package com.nepplus.finalproject_ljh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.finalproject_ljh.databinding.ActivitySignUpBinding
import com.nepplus.finalproject_ljh.datas.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.signUpOkBtn.setOnClickListener {

            val email = binding.emailEdt.text.toString()
            val pw = binding.passwordEdt.text.toString()
            val nickname = binding.nicknameEdt.text.toString()

            apiService.putRequestSignUp(email, pw, nickname).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>) {

                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!

                        Log.d("server", basicResponse.message)
                    } else {

                        val errorBodyStr = response.errorBody()!!.toString()
                        Log.d("server", errorBodyStr)
                        val jsonObj = JSONObject(errorBodyStr)
                        val message = jsonObj.getString("message")

                        Toast.makeText(mContext, "${message}", Toast.LENGTH_SHORT).show()

                    }
                    
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })

        }

    }

    override fun setValues() {

    }
}