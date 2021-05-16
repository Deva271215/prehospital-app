package com.g_one_nursesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.g_one_nursesapp.actions.ConsciousCheckActivity
import com.g_one_nursesapp.actions.InjuryCheckActivity
import com.g_one_nursesapp.actions.RespirationsCheckActivity
import com.g_one_nursesapp.actions.TensiCheckActivity
import kotlinx.android.synthetic.main.activity_list_actions.*

class ListActionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_actions)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Daftar Tindakan"

        setBtnCheckConscious()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setBtnCheckConscious(){
        button_CekPernafasan.setOnClickListener {
            val intent = Intent(this, RespirationsCheckActivity::class.java)
            startActivity(intent)
        }

        button_CekKesadaran.setOnClickListener {
            val intent = Intent(this, ConsciousCheckActivity::class.java)
            startActivity(intent)
        }

        button_CekTensi.setOnClickListener {
            val intent = Intent(this, TensiCheckActivity::class.java)
            startActivity(intent)
        }

        button_CekLuka.setOnClickListener {
            val intent = Intent(this, InjuryCheckActivity::class.java)
            startActivity(intent)
        }

    }

}