package com.g_one_nursesapp.api

import com.g_one_nursesapp.api.response.*
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.entity.SymtompEntity
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

    @GET("hospitals/{lat}/{lon}")
    fun getNearestHospitals(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double,
    ): Call<List<HospitalsResponse>>

    @GET("chats")
    fun getChats(@Header("Authorization") header: String): Call<ArrayList<ChatResponse>>

    @PUT("chats/{id}")
    fun updateChatPrediction(
        @Header("Authorization") header: String,
        @Path("id") id: String,
        @Query("p") p: String,
    ): Call<BasicResponse>

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

    // Prediction
    @POST("predict")
    fun predictSymtomps(
        @Body symtomps: SymtompEntity
    ): Call<ArrayList<PredictionResponse>>
}