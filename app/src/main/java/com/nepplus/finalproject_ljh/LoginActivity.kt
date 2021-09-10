package com.nepplus.finalproject_ljh

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.nepplus.finalproject_ljh.databinding.ActivityLoginBinding
import java.security.MessageDigest
import java.util.*


class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

    }

    override fun setupEvents() {
        
        callbackManager = CallbackManager.Factory.create();

        binding.loginButton.setReadPermissions("email")

        binding.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?>() {
            fun onSuccess(loginResult: LoginResult?) {
                // App code
                Log.d("login", loginResult.toString())

                val accessToken = AccessToken.getCurrentAccessToken()
                Log.d("login", accessToken.toString())
            }

            fun onCancel() {
                // App code
            }

            fun onError(exception: FacebookException?) {
                // App code
            }
        })

        binding.loginFaceBookBtn.setOnClickListener {

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"))

        }

    }

    override fun setValues() {

//        키 해시값 추출
        val info = packageManager.getPackageInfo("com.nepplus.finalproject_ljh",
            PackageManager.GET_SIGNATURES
        )
        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}