package com.g_one_nursesapp.actions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.g_one_nursesapp.ChatFieldActivity
import com.g_one_nursesapp.R
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.viewmodels.AdditionalNoteViewModel
import com.g_one_nursesapp.viewmodels.RespirationCheckViewModel
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class AdditionalNotesActivity : AppCompatActivity() {

    private lateinit var result: String

    private lateinit var additionalNoteViewModel: AdditionalNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional_notes)

        val buttonSubmit = findViewById<TextView>(R.id.button_submite_additional)
        buttonSubmit.setOnClickListener {
            val addInput = loginEmail.text.toString().trim()
            when {

                addInput.isEmpty() -> {
                    Toast.makeText(this, "Catatan belum dimasukan", Toast.LENGTH_LONG).show()
                }

                else -> {
                    val message = MessageEntity(
                        id = UUID.randomUUID().toString(),
                        message = "Catatan Tambahan",
                        result = result,
                    )
                    additionalNoteViewModel.insertOneMessage(message)

                    val intent = Intent(this, ChatFieldActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }


}