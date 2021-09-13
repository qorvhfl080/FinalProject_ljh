package com.nepplus.finalproject_ljh.web

import android.content.Context
import com.nepplus.finalproject_ljh.utils.ContextUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerAPI {

    companion object {
        private val hostURL = "http://3.36.146.152"

        private var retrofit: Retrofit? = null

        fun getRetrofit(context: Context): Retrofit {
            if (retrofit == null) {

//                API 요청 발생 -> 인터셉트 -> 헤더 추가 -> API요청 이어감
                val interceptor = Interceptor {
                    with(it) {

                        val newRequest = request().newBuilder()
                            .addHeader("X-Http-Token", ContextUtil.getToken(context))
                            .build()

                        proceed(newRequest)
                    }
                }

                val myClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

                retrofit = Retrofit.Builder()
                    .baseUrl(hostURL)
                    .client(myClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }

            return retrofit!!
        }

    }

}