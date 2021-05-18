package com.g_one_nursesapp.api

import com.g_one_nursesapp.auth.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("/users")
    fun createUser(
        @Field("email") email: String,
        @Field("group_name") groupName: String,
        @Field("password") password: String,
        @Field("telp") noHp: String,
    ): Call<DefaultResponse>

    @POST("/auth/sign-in")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>
}