package com.g_one_nursesapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // private const val PROD_URL = "https://canary-dot-g-one-db.an.r.appspot.com"
    private const val DEV_URL = "http://192.168.18.127:8080/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .method(original.method, original.body)

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(DEV_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }
}