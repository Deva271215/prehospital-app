package com.g_one_nursesapp.actions

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.g_one_nursesapp.ChatFieldActivity
import com.g_one_nursesapp.R
import com.g_one_nursesapp.databinding.ActivityRespirationsCheckBinding
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.viewmodels.RespirationCheckViewModel
import kotlinx.android.synthetic.main.activity_respirations_check.*
import java.util.*

class RespirationsCheckActivity : AppCompatActivity() {
    private lateinit var checkResult: String
    private lateinit var givenAction: String
    private lateinit var patientResponse: String
    private lateinit var respirationCheckViewModel: RespirationCheckViewModel
    private lateinit var binding: ActivityRespirationsCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRespirationsCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        respirationCheckViewModel = ViewModelProvider(this).get(RespirationCheckViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Periksa Pernafasan"

        // Set respiration checking items
        val spCheckResp = resources.getStringArray(R.array.checking_result_items)
        val adapterRespiration = ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                spCheckResp
        )
        binding.inputCheckResp.adapter = adapterRespiration
        binding.inputCheckResp.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                val value = adapterView?.getItemAtPosition(position).toString()
                checkResult = if (position != 0) value else ""
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                checkResult = ""
            }
        }

        // Set respiration assistant items
        val spRespAssis = resources.getStringArray(R.array.nurse_actions_items)
        val adapterResp = ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                spRespAssis
        )
        inputRespAssis.adapter = adapterResp
        inputRespAssis.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                val value = adapterView?.getItemAtPosition(position).toString()
                givenAction = if (position != 0) value else ""
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                givenAction = ""
            }
        }

        // Set respiration response items
        val spRespon = resources.getStringArray(R.array.patient_response_items)
        val adapterRespon = ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                spRespon
        )
        inputRespon.adapter = adapterRespon
        inputRespon.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                val value = adapterView?.getItemAtPosition(position).toString()
                patientResponse = if (position != 0) value else ""
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                patientResponse = ""
            }
        }

        binding.buttonSubmite.setOnClickListener {
            when {
                checkResult.isEmpty() -> {
                    Toast.makeText(this, "Silahkan masukkan hasil pemeriksaan yang tepat", Toast.LENGTH_LONG).show()
                }
                givenAction.isEmpty() -> {
                    Toast.makeText(this, "Silahkan masukkan tindakan yang tepat", Toast.LENGTH_LONG).show()
                }
                patientResponse.isEmpty() -> {
                    Toast.makeText(this, "Silahkan masukkan respon yang tepat", Toast.LENGTH_LONG).show()
                }
                else -> {
                    // Insert message to local database
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
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}