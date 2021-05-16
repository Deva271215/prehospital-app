package com.g_one_nursesapp.actions

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.g_one_nursesapp.R
import kotlinx.android.synthetic.main.activity_conscious_check.*
import kotlinx.android.synthetic.main.activity_list_actions.*
import kotlinx.android.synthetic.main.activity_tensi_check.*
import kotlinx.android.synthetic.main.fragment_check_conscious.*

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
