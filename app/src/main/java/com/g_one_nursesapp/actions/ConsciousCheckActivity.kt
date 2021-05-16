package com.g_one_nursesapp.actions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.g_one_nursesapp.R
import kotlinx.android.synthetic.main.activity_conscious_check.*
import kotlinx.android.synthetic.main.activity_tensi_check.*
import kotlinx.android.synthetic.main.fragment_check_conscious.*
import kotlinx.android.synthetic.main.fragment_check_conscious.inputEyeRespon

class ConsciousCheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conscious_check)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Periksa Kesadaran"

        val spListEye = listOf("Respon Mata", "Tidak ada respon mata (1)", "Bereaksi saat rangsang nyeri (2)", "Beraksi saat diperintah buka mata (3)", "Mata terbuka normal tanpa rangsangan dan perintah (4)")
        val adapterEye = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spListEye)
        inputEyeRespon.adapter = adapterEye
        inputEyeRespon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                Toast.makeText(
                        this@ConsciousCheckActivity,
                        "You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        val spListVoice = listOf("Respon Suara", "Tidak ada respon suara (1)", "Bereaksi saat rangsang nyeri (2)", "Beraksi saat diperintah buka mata (3)", "Mata terbuka normal tanpa rangsangan dan perintah (4)")
        val adapterVoice = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spListVoice)
        inputVoiceRespon.adapter = adapterVoice
        inputVoiceRespon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                Toast.makeText(
                        this@ConsciousCheckActivity,
                        "You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val spListMove = listOf("Respon Gerakan", "Tidak ada respon gerakan (1)", "Bereaksi saat rangsang nyeri (2)", "Beraksi saat diperintah buka mata (3)", "Mata terbuka normal tanpa rangsangan dan perintah (4)")
        val adapterMove = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spListMove)
        inputMoveRespon.adapter = adapterMove
        inputMoveRespon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                Toast.makeText(
                        this@ConsciousCheckActivity,
                        "You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

//        setButtonSubmit()
    }

//    private fun setButtonSubmit(){
//        button_submite.setOnClickListener {
//            Toast.makeText(this@ConsciousCheckActivity, "Value Submited", Toast.LENGTH_LONG).show()
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}