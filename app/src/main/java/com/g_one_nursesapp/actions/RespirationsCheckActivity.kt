package com.g_one_nursesapp.actions

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.g_one_nursesapp.R
import kotlinx.android.synthetic.main.activity_conscious_check.*
import kotlinx.android.synthetic.main.activity_respirations_check.*
import kotlinx.android.synthetic.main.activity_tensi_check.*
import kotlinx.android.synthetic.main.fragment_check_conscious.*

class RespirationsCheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_respirations_check)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Periksa Pernafasan"

        val spCheckResp = listOf("Hasil Pemeriksaan", "Tersumbat benda asing/cairan", "Nafas Tersendat/Tidak Lancar", "Seluran Nafasan Normal")
        val adapterRespiration = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spCheckResp)
        inputCheckResp.adapter = adapterRespiration
        inputCheckResp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                Toast.makeText(
                        this@RespirationsCheckActivity,
                        "Anda memilih ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val spRespAssis = listOf("Bantuan pernafasan yang diberikan", "Resutasi Jantung Paru (RJP)", "Pemberian Oksigen", "Membuka saluran nafas", "Membuka saluran nafas dan RJP")
        val adapterResp = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spRespAssis)
        inputRespAssis.adapter = adapterResp
        inputRespAssis.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                Toast.makeText(
                        this@RespirationsCheckActivity,
                        "Anda memilih ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val spRespon = listOf("Respon pasien setelah bantuan", "Pernafasan berangsur nomal", "Pernafasan masih terganggu")
        val adapterRespon = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spRespon)
        inputRespon.adapter = adapterRespon
        inputRespon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                Toast.makeText(
                        this@RespirationsCheckActivity,
                        "Anda memilih ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

//        setButtonSubmit()
    }

//    private fun setButtonSubmit(){
//        button_submite.setOnClickListener {
//            Toast.makeText(this@RespirationsCheckActivity, "Value Submited", Toast.LENGTH_LONG).show()
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}