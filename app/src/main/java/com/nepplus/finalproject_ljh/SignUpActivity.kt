package com.nepplus.finalproject_ljh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.finalproject_ljh.databinding.ActivitySignUpBinding

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
            apiService.putRequestSignUp(email, pw, nickname)

        }

    }

    override fun setValues() {

    }
}