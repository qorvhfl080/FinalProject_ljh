package com.nepplus.finalproject_ljh.web

import com.nepplus.finalproject_ljh.datas.AppointmentData
import com.nepplus.finalproject_ljh.datas.BasicResponse
import retrofit2.Call
import retrofit2.http.*

interface ServerAPIService {

    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(@Field("email")email: String,
                         @Field("password")pw: String,
                         @Field("nick_name")nickname: String): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(@Field("email")email: String,
                         @Field("password")pw: String): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/social")
    fun postRequestSocialLogin(@Field("provider")provider: String,
                               @Field("uid")id: String,
                               @Field("nick_name")name: String): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/appointment")
    fun postRequestAppointment(@Field("title")title: String,
                               @Field("datetime")datetime: String,
                               @Field("place")placeName: String,
                               @Field("latitude")lat: Double,
                               @Field("longitude")lng: Double): Call<BasicResponse>

    @GET("/appointment")
    fun getRequestAppointmentList(): Call<BasicResponse>

    @GET("/user")
    fun getRequestMyInfo(): Call<BasicResponse>

    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestMyInfo(@Field("field")field: String,
                           @Field("value")value: String): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/place")
    fun postRequestAddMyPlace(@Field("name")name: String,
                              @Field("latitude")lat: Double,
                              @Field("longitude")lng: Double,
                              @Field("is_primary")isPrimary: Boolean): Call<BasicResponse>

}