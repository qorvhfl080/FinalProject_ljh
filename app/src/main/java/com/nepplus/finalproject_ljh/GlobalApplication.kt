package com.nepplus.finalproject_ljh

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "8ce5fd66834586e0845dc4bc1b3aa8d4")
    }

}