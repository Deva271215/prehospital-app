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
import com.g_one_nursesapp.api.response.ChatResponse
import com.g_one_nursesapp.databinding.ActivityRespirationsCheckBinding
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.preference.UserPreference
import com.g_one_nursesapp.utility.SocketIOInstance
import com.g_one_nursesapp.viewmodels.RespirationCheckViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_respirations_check.*
import java.util.*

class RespirationsCheckActivity : AppCompatActivity() {
    private lateinit var checkResult: String
    private lateinit var givenAction: String
    private lateinit var patientResponse: String
    private lateinit var respirationCheckViewModel: RespirationCheckViewModel
    private lateinit var binding: ActivityRespirationsCheckBinding
    private lateinit var preference: UserPreference
    private val socket = SocketIOInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = UserPreference(applicationContext)
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

                    if (preference.getIsHospitalSelected()) {
                        emitMessageToSocket(message)
                    }
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

    private fun emitMessageToSocket(message: MessageEntity) {
        val activeChat = preference.getActiveChat()
        message.chat = Gson().fromJson("""$activeChat""", ChatResponse::class.java)
        socket.initSocket()
        socket.connectToSocket()
        socket.getSocket()?.emit("send_message", Gson().toJson(message))
    }
}