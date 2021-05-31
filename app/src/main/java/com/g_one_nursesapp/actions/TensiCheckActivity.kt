package com.g_one_nursesapp.actions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.g_one_nursesapp.R

class TensiCheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tensi_check)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cek Tensi"

//        setButtonSubmit()
    }

//    private fun setButtonSubmit(){
//        button_submite.setOnClickListener {
//            Toast.makeText(this@TensiCheckActivity, "Value Submited", Toast.LENGTH_LONG).show()
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
