package com.g_one_nursesapp.faskes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.g_one_nursesapp.ChatFieldActivity
import com.g_one_nursesapp.adapters.RecomendFaskesAdapter
import com.g_one_nursesapp.api.RetrofitClient
import com.g_one_nursesapp.api.response.HospitalsResponse
import com.g_one_nursesapp.databinding.ActivityRecomendFaskesBinding
import com.g_one_nursesapp.preference.UserPreference
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_recomend_faskes.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecomendFaskesActivity : AppCompatActivity() {
    private lateinit var preference: UserPreference
    private lateinit var binding: ActivityRecomendFaskesBinding
    private lateinit var adapter: RecomendFaskesAdapter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    companion object {
        const val PERMISSION_ID = 1010
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = UserPreference(applicationContext)
        binding = ActivityRecomendFaskesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fused location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        requestPermission()
        getLastLocation()

        // Adapter
        adapter = RecomendFaskesAdapter()
        rv_hospitals.layoutManager = LinearLayoutManager(applicationContext)
        rv_hospitals.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Rekomendasi Faskes"

        displayHospitals()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val intent = Intent(this, ChatFieldActivity::class.java)
        startActivity(intent)
    }

    private fun displayHospitals() {
        RetrofitClient.instance.getNearestHospitals(latitude, longitude).enqueue(object: Callback<List<HospitalsResponse>> {
            override fun onResponse(
                    call: Call<List<HospitalsResponse>>,
                    response: Response<List<HospitalsResponse>>
            ) {
                if (response.isSuccessful) { adapter.setHospitals(response.body()!!) }
            }

            override fun onFailure(call: Call<List<HospitalsResponse>>, t: Throwable) {
                Log.i("Error on fetching hospitals", t.message.toString())
                Toast.makeText(this@RecomendFaskesActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ), PERMISSION_ID)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                    val location: Location? = it.result
                    if (location == null) {
                        newLocationData()
                    } else {
                        latitude = location.latitude
                        longitude = location.longitude
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on your device location", Toast.LENGTH_LONG).show()
            }
        } else {
            requestPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun newLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
            val lastLocation = p0?.lastLocation
            latitude = lastLocation?.latitude!!
            longitude = lastLocation?.longitude!!
        }
    }
}