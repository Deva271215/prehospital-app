package com.g_one_nursesapp.actions

import android.app.AlertDialog
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
import com.g_one_nursesapp.api.response.UserResponse
import com.g_one_nursesapp.databinding.ActivityConsciousCheckBinding
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.preference.UserPreference
import com.g_one_nursesapp.utility.SocketIOInstance
import com.g_one_nursesapp.viewmodels.ConsciousCheckViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_conscious_check.*
import kotlinx.android.synthetic.main.activity_injury_check.*
import java.util.*

class ConsciousCheckActivity : AppCompatActivity() {
    private var eyeResponseNum: Int = 0
    private var voiceResponseNum: Int = 0
    private var movementResponseNum: Int = 0
    private var resultCount: Int = 0
    private val additionalInjuryList = ArrayList<Int>()
    private lateinit var additionalInput: String
    private lateinit var consciousCheckViewModel: ConsciousCheckViewModel
    private lateinit var binding: ActivityConsciousCheckBinding
    private lateinit var preference: UserPreference
    private val socket = SocketIOInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = UserPreference(applicationContext)
        binding = ActivityConsciousCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        consciousCheckViewModel = ViewModelProvider(this).get(ConsciousCheckViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Periksa Kesadaran"

        val spListEyeResponses = resources.getStringArray(R.array.eye_responses)
        val adapterEye = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spListEyeResponses)
        inputEyeRespon.adapter = adapterEye
        inputEyeRespon.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                eyeResponseNum = if (position != 0) position else 0
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        val spListVoice = resources.getStringArray(R.array.voice_response)
        val adapterVoice = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spListVoice)
        inputVoiceRespon.adapter = adapterVoice
        inputVoiceRespon.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                voiceResponseNum = if (position != 0) position else 0
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        val spListMove = resources.getStringArray(R.array.movement_response)
        val adapterMove = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spListMove)
        inputMoveRespon.adapter = adapterMove
        inputMoveRespon.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                movementResponseNum = if (position != 0) position else 0
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        onSelectAdditionalInjury()

        binding.buttonSubmite.setOnClickListener {
            resultCount = eyeResponseNum + voiceResponseNum + movementResponseNum
            when {
                eyeResponseNum == 0 -> {
                    Toast.makeText(this, "Respon mata belum dimasukan", Toast.LENGTH_LONG)
                            .show()
                }
                voiceResponseNum == 0 -> {
                    Toast.makeText(this, "Respon suara belum dimasukan", Toast.LENGTH_LONG)
                            .show()
                }
                movementResponseNum == 0 -> {
                    Toast.makeText(this, "Respon gerakan belum dimasukan", Toast.LENGTH_LONG)
                            .show()
                }
                else -> {
                    // Create message
                    val message = MessageEntity(
                        id = UUID.randomUUID().toString(),
                        message = "Periksa kesadaran",
                        result = "GCS poin $resultCount",
                        condition = additionalInput
                    )
                    consciousCheckViewModel.insertOneMessage(message)

                    if (preference.getIsHospitalSelected()) {
                        emitMessageToSocket(message)
                    }
                    val intent = Intent(this, ChatFieldActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun onSelectAdditionalInjury() {
        val injuryItems = resources.getStringArray(R.array.additional_injury_items)
        val selectedAdditionalInjuries = BooleanArray(injuryItems.size)
        // Initialize dialog
        binding.selectAdditionalInputConcious.isFocusable = false
        binding.selectAdditionalInputConcious.isClickable = true
        binding.selectAdditionalInputConcious.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pilih cidera")
            builder.setCancelable(false)
            builder.setMultiChoiceItems(injuryItems, selectedAdditionalInjuries) { _, which, isChecked ->
                if (isChecked) {
                    additionalInjuryList.add(which)
                    additionalInjuryList.sort()
                } else {
                    additionalInjuryList.remove(which)
                }
            }
            builder.setNegativeButton("Batal") { dialog, _ -> dialog?.dismiss() }
            builder.setPositiveButton("Pilih") { _, _ ->
                val items = ArrayList<String>()
                for (injury in additionalInjuryList) {
                    items.add(injuryItems[injury])
                }
                additionalInput = items.joinToString { "$it" }
                selectAdditionalInputConcious.setText(items.joinToString { "$it" })
            }
            builder.show()
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