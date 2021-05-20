package com.g_one_nursesapp.api

import com.g_one_nursesapp.api.response.LoginResponse
import com.g_one_nursesapp.entity.UserEntity
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("/users")
    fun createUser(
        @Field("email") email: String,
        @Field("group_name") groupName: String,
        @Field("password") password: String,
        @Field("telp") noHp: String,
    ): Call<DefaultResponse>

    @POST("auth/sign-in")
    @Headers("Content-Type: application/json", "Accept: application/json")
    fun userLogin(
            @Body user: UserEntity
    ): Call<LoginResponse>
}