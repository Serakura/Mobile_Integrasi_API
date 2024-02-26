package com.example.tbesar.api

import android.util.Base64.NO_WRAP
import android.util.Base64
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object RetrofitClient {
    private val AUTH = "Basic"+ Base64.encodeToString("firdaus:123456".toByteArray(), Base64.NO_WRAP)

    private const val BASE_URL = "http://192.168.100.134/recipes_apk/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{ chain ->
        val  original = chain.request()

            val requestBuilder = original.newBuilder()
            .addHeader("Authorization",   AUTH)
            .method(original.method(), original.body())

        val request = requestBuilder.build()
         chain.proceed(request)
        }.build()

    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }
}