package com.g_one_nursesapp.actions

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.g_one_nursesapp.ChatFieldActivity
import com.g_one_nursesapp.R
import com.g_one_nursesapp.database.AppRepository
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.viewmodels.RespirationCheckViewModel
import kotlinx.android.synthetic.main.activity_conscious_check.*
import kotlinx.android.synthetic.main.activity_respirations_check.*
import kotlinx.android.synthetic.main.activity_tensi_check.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RespirationsCheckActivity : AppCompatActivity() {
    private lateinit var checkResult: String
    private lateinit var givenAction: String
    private lateinit var patientResponse: String

    private lateinit var respirationCheckViewModel: RespirationCheckViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_respirations_check)

        respirationCheckViewModel = ViewModelProvider(this).get(RespirationCheckViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Periksa Pernafasan"

        val spCheckResp = listOf("Hasil Pemeriksaan", "Tersumbat benda asing/cairan", "Nafas Tersendat/Tidak Lancar", "Seluran Nafasan Normal")
        val adapterRespiration = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spCheckResp)
        inputCheckResp.adapter = adapterRespiration
        inputCheckResp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                checkResult = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                checkResult = ""
            }
        }

        val spRespAssis = listOf("Bantuan pernafasan yang diberikan", "Resutasi Jantung Paru (RJP)", "Pemberian Oksigen", "Membuka saluran nafas", "Membuka saluran nafas dan RJP")
        val adapterResp = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spRespAssis)
        inputRespAssis.adapter = adapterResp
        inputRespAssis.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                givenAction = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                givenAction = ""
            }
        }

        val spRespon = listOf("Respon pasien setelah bantuan", "Pernafasan berangsur nomal", "Pernafasan masih terganggu")
        val adapterRespon = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spRespon)
        inputRespon.adapter = adapterRespon
        inputRespon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                patientResponse = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                patientResponse = ""
            }
        }

        val buttonSubmit = findViewById<TextView>(R.id.button_submite)
        buttonSubmit.setOnClickListener {
            val message = MessageEntity(
                id = UUID.randomUUID().toString(),
                message = "Periksa pernafasan",
                result = checkResult,
                response = patientResponse,
                action = givenAction,
            )
            respirationCheckViewModel.insertOneMessage(message)

            val intent = Intent(this, ChatFieldActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}