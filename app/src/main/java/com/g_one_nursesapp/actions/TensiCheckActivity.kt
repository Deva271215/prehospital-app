package com.g_one_nursesapp.actions

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.g_one_nursesapp.ChatFieldActivity
import com.g_one_nursesapp.api.response.ChatResponse
import com.g_one_nursesapp.databinding.ActivityTensiCheckBinding
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.preference.UserPreference
import com.g_one_nursesapp.utility.SocketIOInstance
import com.g_one_nursesapp.viewmodels.TensiCheckViewModel
import com.google.gson.Gson
import java.util.*

class TensiCheckActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTensiCheckBinding
    private lateinit var tensiCheckViewModel: TensiCheckViewModel
    private lateinit var preference: UserPreference
    private val socket = SocketIOInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = UserPreference(applicationContext)
        binding = ActivityTensiCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tensiCheckViewModel = ViewModelProvider(this).get(TensiCheckViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cek Tensi"

        onSubmitButtonClicked()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun onSubmitButtonClicked() {
        binding.buttonSubmite.setOnClickListener {
            val etSystole = binding.inputSystole.text.toString()
            val etDiastole = binding.inputDiastole.text.toString()

            when {
                etSystole.isNullOrEmpty() -> {
                    Toast.makeText(this, "Input systole tidak boleh kosong", Toast.LENGTH_LONG).show()
                }
                etDiastole.isNullOrEmpty() -> {
                    Toast.makeText(this, "Input diastole tidak boleh kosong", Toast.LENGTH_LONG).show()
                }
                else -> {
                    // Create message
                    val message = MessageEntity(
                        id = UUID.randomUUID().toString(),
                        message = "Cek Tensi",
                        result = "Systole: $etSystole, Diastole: $etDiastole",
                    )
                    tensiCheckViewModel.insertOneMessage(message)

                    if (preference.getIsHospitalSelected()) {
                        emitMessageToSocket(message)
                    }
                    val intent = Intent(this, ChatFieldActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun emitMessageToSocket(message: MessageEntity) {
        val activeChat = preference.getActiveChat()
        message.chat = Gson().fromJson("""$activeChat""", ChatResponse::class.java)
        socket.initSocket()
        socket.connectToSocket()
        socket.getSocket()?.emit("send_message", Gson().toJson(message))
    }
}
