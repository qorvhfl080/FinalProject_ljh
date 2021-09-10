package com.nepplus.finalproject_ljh

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.kakao.sdk.user.UserApiClient
import com.nepplus.finalproject_ljh.databinding.ActivityLoginBinding
import org.json.JSONObject
import java.security.MessageDigest
import java.util.*


class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.signUpBtn.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)

        }

        binding.loginKakaoBtn.setOnClickListener {

            UserApiClient.instance.loginWithKakaoAccount(mContext) { token, error ->
                if (error != null) {
                    Log.d("kakao", error.toString())
                } else if (token != null) {
                    Log.d("kakao", "토큰 : ${token.accessToken}")

                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.d("kakao", "사용자 정보 요청 실패")
                        } else if (user != null) {
                            Log.d("kakao", "사용자 정보 요청 성공" +
                            "\n${user.id}" +
                            "\n${user.kakaoAccount?.email}" +
                            "\n${user.kakaoAccount?.profile?.nickname}" +
                            "\n${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                        }
                    }
                }
            }

        }

        callbackManager = CallbackManager.Factory.create();

        binding.loginButton.setReadPermissions("email")

        binding.loginFacebookBtn.setOnClickListener {

            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

                override fun onSuccess(result: LoginResult?) {
                    Log.d("login", "로그인 성공")

                    val graphRequest = GraphRequest.newMeRequest(result?.accessToken, object : GraphRequest.GraphJSONObjectCallback {
                        override fun onCompleted(jsonObj: JSONObject?, response: GraphResponse?) {
                            Log.d("login", jsonObj.toString())

                            val name = jsonObj!!.getString("name")
                            val id = jsonObj.getString("id")

                            Log.d("login", name)
                            Log.d("login", id)

                            //Todo - 페북이 알려준 name, id를 api서버에 전달해서 소셜로그인 처리

                        }
                    })

                    graphRequest.executeAsync()

                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException?) {
                    // App code
                }
            })

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"))

        }

//        binding.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
//
//            override fun onSuccess(loginResult: LoginResult?) {
//                // App code
//                Log.d("login", loginResult.toString())
//
//                val accessToken = AccessToken.getCurrentAccessToken()
//                Log.d("login", accessToken.toString())
//            }
//
//            override fun onCancel() {
//                // App code
//            }
//
//            override fun onError(exception: FacebookException?) {
//                // App code
//            }
//        })



    }

    override fun setValues() {

//        키 해시값 추출
        val info = packageManager.getPackageInfo("com.nepplus.finalproject_ljh",
            PackageManager.GET_SIGNATURES)

        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.d("keyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}