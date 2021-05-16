package com.g_one_nursesapp.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.g_one_nursesapp.R

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Daftar"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}