package com.g_one_nursesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.g_one_nursesapp.adapters.PredictionsAdapter
import com.g_one_nursesapp.api.RetrofitClient
import com.g_one_nursesapp.api.response.BasicResponse
import com.g_one_nursesapp.api.response.ChatResponse
import com.g_one_nursesapp.api.response.PredictionResponse
import com.g_one_nursesapp.databinding.ActivityPredictionsBinding
import com.g_one_nursesapp.preference.UserPreference
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictionsActivity : AppCompatActivity() {
    companion object {
        const val PREDICTION = "PREDICTION"
    }

    private lateinit var binding: ActivityPredictionsBinding
    private lateinit var adapter: PredictionsAdapter
    private lateinit var preference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionsBinding.inflate(layoutInflater)
        preference = UserPreference(applicationContext)
        setContentView(binding.root)
        
        // Adapter
        adapter = PredictionsAdapter()
        binding.rvPredictions.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvPredictions.adapter = adapter

        val predictions = intent.getStringExtra(PREDICTION)
        val arrayListPredictionsType = object: TypeToken<ArrayList<PredictionResponse>>() {}.type
        val res = Gson().fromJson<ArrayList<PredictionResponse>>("""$predictions""", arrayListPredictionsType)
        val splitList = res.subList(0, 3)
        adapter.setPredictions(splitList)

        updateChatPrediction(splitList[0])

        binding.buttonFinish.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateChatPrediction(prediction: PredictionResponse) {
        val token = preference.getLoginData()?.access_token
        val chatId = Gson().fromJson(preference.getActiveChat(), ChatResponse::class.java).id
        RetrofitClient.instance.updateChatPrediction("Bearer $token", chatId, prediction.label).enqueue(object: Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                Toast.makeText(this@PredictionsActivity, response.body()?.message, Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Toast.makeText(this@PredictionsActivity, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

}