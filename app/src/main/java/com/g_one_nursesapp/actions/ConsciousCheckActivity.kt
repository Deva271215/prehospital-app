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
import com.g_one_nursesapp.databinding.ActivityConsciousCheckBinding
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.viewmodels.ConsciousCheckViewModel
import kotlinx.android.synthetic.main.activity_conscious_check.*
import java.util.*

class ConsciousCheckActivity : AppCompatActivity() {
    private var eyeResponseNum: Int = 0
    private var voiceResponseNum: Int = 0
    private var movementResponseNum: Int = 0
    private var resultCount: Int = 0
    private lateinit var consciousCheckViewModel: ConsciousCheckViewModel
    private lateinit var binding: ActivityConsciousCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    )
                    consciousCheckViewModel.insertOneMessage(message)

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