package com.g_one_nursesapp.actions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.g_one_nursesapp.ChatFieldActivity
import com.g_one_nursesapp.R
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.viewmodels.ConsciousCheckViewModel
import kotlinx.android.synthetic.main.activity_conscious_check.*
import kotlinx.android.synthetic.main.activity_tensi_check.*
import kotlinx.android.synthetic.main.fragment_check_conscious.*
import kotlinx.android.synthetic.main.fragment_check_conscious.inputEyeRespon
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ConsciousCheckActivity : AppCompatActivity() {
    private var eyeResponseNum: Int = 0
    private var voiceResponseNum: Int = 0
    private var movementResponseNum: Int = 0
    private var resultCount: Int = 0

    private lateinit var consciousCheckViewModel: ConsciousCheckViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conscious_check)

        consciousCheckViewModel = ViewModelProvider(this).get(ConsciousCheckViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Periksa Kesadaran"

        val spListEyeResponses = resources.getStringArray(R.array.eye_responses)
        val adapterEye = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, spListEyeResponses)
        inputEyeRespon.adapter = adapterEye
        inputEyeRespon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                eyeResponseNum = position + 1
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }


        val spListVoice = resources.getStringArray(R.array.voice_response)
        val adapterVoice = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spListVoice)
        inputVoiceRespon.adapter = adapterVoice
        inputVoiceRespon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                voiceResponseNum = position + 1
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        val spListMove = resources.getStringArray(R.array.movement_response)
        val adapterMove = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spListMove)
        inputMoveRespon.adapter = adapterMove
        inputMoveRespon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                movementResponseNum = position + 1
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        val buttonSubmit = findViewById<TextView>(R.id.button_submite)
        buttonSubmit.setOnClickListener {
            resultCount = eyeResponseNum + voiceResponseNum + movementResponseNum

            // Create message
            val message = MessageEntity(
                    id = UUID.randomUUID().toString(),
                    message = "Periksa kesadaran",
                    result = "$resultCount poin",
            )
            consciousCheckViewModel.insertOneMessage(message)

            val intent = Intent(this, ChatFieldActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}