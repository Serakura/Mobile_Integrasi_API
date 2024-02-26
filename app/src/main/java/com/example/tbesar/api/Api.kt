package com.example.tbesar.api

import com.example.tbesar.model.login.login
import com.example.tbesar.model.register.register
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("register.php")
    fun createUser(
        @Field("nama") nama:String,
        @Field("no_hp") nohp:String,
        @Field("username") username:String,
        @Field("password") password:String
    ):Call<register>

    @FormUrlEncoded
    @POST("login.php")
    fun userLogin(
        @Field("username") username:String,
        @Field("password") password:String
    ):Call<login>
}