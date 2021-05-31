package com.g_one_nursesapp.actions

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.g_one_nursesapp.ChatFieldActivity
import com.g_one_nursesapp.R
import com.g_one_nursesapp.api.response.ChatResponse
import com.g_one_nursesapp.databinding.ActivityInjuryCheckBinding
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.faskes.RecomendFaskesActivity
import com.g_one_nursesapp.preference.UserPreference
import com.g_one_nursesapp.utility.SocketIOInstance
import com.g_one_nursesapp.viewmodels.InjuryCheckViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_injury_check.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class InjuryCheckActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInjuryCheckBinding
    private lateinit var injuryCheckViewModel: InjuryCheckViewModel
    private lateinit var preference: UserPreference
    private lateinit var result: String
    private val injuriesList = ArrayList<Int>()
    private val socket = SocketIOInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInjuryCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = UserPreference(applicationContext)

        injuryCheckViewModel = ViewModelProvider(this).get(InjuryCheckViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Periksa Cidera"

        onSelectInjuriesInput()
        onSubmitButtonClicked()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun onSelectInjuriesInput() {
        val injuryItems = resources.getStringArray(R.array.injury_items)
        val selectedInjuries = BooleanArray(injuryItems.size)
        // Initialize dialog
        binding.selectInjuriesInput.isFocusable = false
        binding.selectInjuriesInput.isClickable = true
        binding.selectInjuriesInput.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pilih cidera")
            builder.setCancelable(false)
            builder.setMultiChoiceItems(injuryItems, selectedInjuries) { _, which, isChecked ->
                if (isChecked) {
                    injuriesList.add(which)
                    injuriesList.sort()
                } else {
                    injuriesList.remove(which)
                }
            }
            builder.setNegativeButton("Batal") { dialog, _ -> dialog?.dismiss() }
            builder.setPositiveButton("Pilih") { _, _ ->
                val items = ArrayList<String>()
                for (injury in injuriesList) {
                    items.add(injuryItems[injury])
                }
                result = items.joinToString { "$it" }
                selectInjuriesInput.setText(items.joinToString { "$it" })
            }
            builder.show()
        }
    }

    private fun onSubmitButtonClicked() {
        binding.buttonSubmite.setOnClickListener {
            // Create new message and store to local database
            val message = MessageEntity(
                    id = UUID.randomUUID().toString(),
                    message = "Periksa Cidera",
                    result = result,
            )
            injuryCheckViewModel.insertOneMessage(message)

            if(preference.getIsHospitalSelected()) {
                val intent = Intent(this, ChatFieldActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, RecomendFaskesActivity::class.java)
                startActivity(intent)
            }
        }
    }

//    private fun emitMessageToSocket(message: MessageEntity) {
//        val activeChat = preference.getActiveChat()
//        message.chat = Gson().fromJson("""$activeChat""", ChatResponse::class.java)
//        socket.initSocket()
//        socket.connectToSocket()
//        socket.getSocket()?.emit("send_message", Gson().toJson(message))
//    }
}
