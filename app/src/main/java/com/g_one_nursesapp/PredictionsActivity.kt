package com.g_one_nursesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.g_one_nursesapp.adapters.PredictionsAdapter
import com.g_one_nursesapp.api.response.PredictionResponse
import com.g_one_nursesapp.databinding.ActivityPredictionsBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PredictionsActivity : AppCompatActivity() {
    companion object {
        const val PREDICTION = "PREDICTION"
    }

    private lateinit var binding: ActivityPredictionsBinding
    private lateinit var adapter: PredictionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Adapter
        adapter = PredictionsAdapter()
        binding.rvPredictions.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvPredictions.adapter = adapter

        val predictions = intent.getStringExtra(PREDICTION)
        val arrayListPredictionsType = object: TypeToken<ArrayList<PredictionResponse>>() {}.type
        val res = Gson().fromJson<ArrayList<PredictionResponse>>("""$predictions""", arrayListPredictionsType)
        adapter.setPredictions(res)

        binding.buttonFinish.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }


}