package com.nepplus.finalproject_ljh.web

import com.nepplus.finalproject_ljh.datas.AppointmentData
import com.nepplus.finalproject_ljh.datas.BasicResponse
import okhttp3.MultipartBody
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
                               @Field("start_place")StartPlaceName: String,
                               @Field("start_latitude")StartLat: Double,
                               @Field("start_longitude")StartLng: Double,
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

    @GET("/user/place")
    fun getRequestMyPlaceList(): Call<BasicResponse>

    @Multipart
    @PUT("/user/image")
    fun putRequestProfileImg(@Part profileImg: MultipartBody.Part): Call<BasicResponse>

    @GET("/user/friend")
    fun getRequestFriendList(@Query("type")type: String): Call<BasicResponse>

    @GET("/search/user")
    fun getRequestSearchUser(@Query("nickname")keyword: String): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/friend")
    fun postRequestAddFriend(@Field("user_id")userId: Int): Call<BasicResponse>

    @FormUrlEncoded
    @PUT("/user/friend")
    fun putRequestSendOkOrNoFriend(@Field("user_id")userId: Int,
                                   @Field("type")type: String): Call<BasicResponse>

}