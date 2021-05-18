package com.g_one_nursesapp.api.remote

import android.util.Log
import com.g_one_nursesapp.auth.signup.SignUpResponse
import com.g_one_nursesapp.utility.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RemoteDataSource {

    fun getAllSignUp() {
        val client = ApiConfig.getApiService().signUpUser()
        EspressoIdlingResource.increment()
        client.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                if (response.isSuccessful) {
                } else {
                    Log.d("Error", "getAllMovies onResponse = ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.d("Error", "getAllMovies onFailure = ${t.message.toString()}")
            }

        })
    }
}