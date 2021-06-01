package com.g_one_nursesapp.actions

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.g_one_nursesapp.ChatFieldActivity
import com.g_one_nursesapp.R
import com.g_one_nursesapp.api.response.ChatResponse
import com.g_one_nursesapp.databinding.ActivityAdditionalNotesBinding
import com.g_one_nursesapp.databinding.ActivityInjuryCheckBinding
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.faskes.RecomendFaskesActivity
import com.g_one_nursesapp.preference.UserPreference
import com.g_one_nursesapp.utility.SocketIOInstance
import com.g_one_nursesapp.viewmodels.AdditionalNoteViewModel
import com.g_one_nursesapp.viewmodels.InjuryCheckViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_additional_notes.*
import kotlinx.android.synthetic.main.activity_injury_check.*
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class AdditionalNotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdditionalNotesBinding
    private lateinit var preference: UserPreference
    private val additionalInjuriesList = ArrayList<Int>()
    private val socket = SocketIOInstance()
    private lateinit var result: String
    private lateinit var additionalNoteViewModel: AdditionalNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditionalNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = UserPreference(applicationContext)
        additionalNoteViewModel = ViewModelProvider(this).get(AdditionalNoteViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Catatan Tambahan"

        onSelectInjuriesInput()
        onSubmitButtonClicked()

    }

    private fun onSelectInjuriesInput() {
        val injuryItems = resources.getStringArray(R.array.additional_injury_items)
        val selectedInjuries = BooleanArray(injuryItems.size)

        // Initialize dialog
        binding.selectAdditionalInput.isFocusable = false
        binding.selectAdditionalInput.isClickable = true
        binding.selectAdditionalInput.setOnClickListener {

            val builder = AlertDialog.Builder(this)

            builder.setTitle("Masukan kondisi lainnya dari pasien")
            builder.setCancelable(false)

            builder.setMultiChoiceItems(injuryItems, selectedInjuries) { _, which, isChecked ->
                if (isChecked) {
                    additionalInjuriesList.add(which)
                    additionalInjuriesList.sort()
                } else {
                    additionalInjuriesList.remove(which)
                }
            }

            builder.setNegativeButton("Batal") { dialog, _ -> dialog?.dismiss() }
            builder.setPositiveButton("Pilih") { _, _ ->
                val items = ArrayList<String>()
                for (injury in additionalInjuriesList) {
                    items.add(injuryItems[injury])
                }
                result = items.joinToString { "$it" }
                selectAdditionalInput.setText(items.joinToString { "$it" })
            }
            builder.show()
        }
    }

    private fun onSubmitButtonClicked() {
        binding.buttonSubmiteAdditional.setOnClickListener {
            val inputOtherAdditional = inputOtherAdditional.text.toString().trim()
            // Create new message and store to local database
            val message = MessageEntity(
                    id = UUID.randomUUID().toString(),
                    message = "Catatan Tambahan",
                    result = "$result, $inputOtherAdditional",

            )
            additionalNoteViewModel.insertOneMessage(message)

            if(preference.getIsHospitalSelected()) {
                emitMessageToSocket(message)
                val intent = Intent(this, ChatFieldActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, ChatFieldActivity::class.java)
                startActivity(intent)
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