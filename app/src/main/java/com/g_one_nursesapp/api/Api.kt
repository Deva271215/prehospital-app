package com.g_one_nursesapp.api

import com.g_one_nursesapp.api.response.*
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.entity.UserEntity
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @POST("/users")
    fun createUser(
            @Body user: UserEntity
    ): Call<SignUpResponse>

    @POST("auth/sign-in")
    @Headers("Content-Type: application/json", "Accept: application/json")
    fun userLogin(
            @Body user: UserEntity
    ): Call<LoginResponse>

    @GET("hospitals")
    fun getHospitals(@Header("Authorization") header: String): Call<List<HospitalsResponse>>

    @GET("chats")
    fun getChats(@Header("Authorization") header: String): Call<ArrayList<ChatResponse>>

    @GET("chats/hospital/{id}")
    fun getChatsByHospital(
        @Path("id") id: String,
        @Header("Authorization") header: String
    ): Call<ArrayList<ChatResponse>>

    // Message
    @GET("messages/chat/{id}")
    fun getMessages(
            @Path("id") id: String,
            @Header("Authorization") header: String
    ): Call<ArrayList<MessageEntity>>
}