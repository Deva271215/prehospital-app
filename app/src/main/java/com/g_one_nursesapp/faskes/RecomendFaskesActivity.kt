package com.g_one_nursesapp.faskes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.g_one_nursesapp.ChatFieldActivity
import com.g_one_nursesapp.R
import com.g_one_nursesapp.adapters.RecomendFaskesAdapter
import com.g_one_nursesapp.api.RetrofitClient
import com.g_one_nursesapp.api.response.HospitalsResponse
import com.g_one_nursesapp.databinding.ActivityRecomendFaskesBinding
import com.g_one_nursesapp.preference.UserPreference
import kotlinx.android.synthetic.main.activity_recomend_faskes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecomendFaskesActivity : AppCompatActivity() {
    private lateinit var preference: UserPreference
    private lateinit var binding: ActivityRecomendFaskesBinding
    private lateinit var adapter: RecomendFaskesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendFaskesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = UserPreference(applicationContext)

        // Adapter
        adapter = RecomendFaskesAdapter()
        rv_hospitals.layoutManager = LinearLayoutManager(applicationContext)
        rv_hospitals.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Rekomendasi Faskes"

        val user = preference.getLoginData()
        RetrofitClient.instance.getHospitals("Bearer ${user.access_token}").enqueue(object: Callback<List<HospitalsResponse>> {
            override fun onResponse(
                call: Call<List<HospitalsResponse>>,
                response: Response<List<HospitalsResponse>>
            ) {
                Log.i("Hospitals", response.body().toString())
                if (response.isSuccessful) {
                    adapter.setHospital(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<HospitalsResponse>>, t: Throwable) {
                Log.i("Hospitals", t.message.toString())
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val intent = Intent(this, ChatFieldActivity::class.java)
        startActivity(intent)
    }
}