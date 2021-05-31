package com.g_one_nursesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.g_one_nursesapp.adapters.HistoryAdapter
import com.g_one_nursesapp.api.RetrofitClient
import com.g_one_nursesapp.api.response.ChatResponse
import com.g_one_nursesapp.databinding.ActivityHistoryBinding
import com.g_one_nursesapp.preference.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private lateinit var preference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = UserPreference(applicationContext)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Riwayat Pertolongan"

        // Adapter
        adapter = HistoryAdapter()
        binding.rvHistory.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvHistory.adapter = adapter

        loadChatsHistory()
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun loadChatsHistory() {
        val token = preference.getLoginData()?.access_token
        RetrofitClient.instance.getChats("Bearer $token").enqueue(object:
            Callback<ArrayList<ChatResponse>> {
            override fun onResponse(
                call: Call<ArrayList<ChatResponse>>,
                response: Response<ArrayList<ChatResponse>>
            ) {
                if (response.isSuccessful) {
                    adapter.setChats(response.body()!!)
                }
            }

            override fun onFailure(call: Call<ArrayList<ChatResponse>>, t: Throwable) {
                Toast.makeText(this@HistoryActivity, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}