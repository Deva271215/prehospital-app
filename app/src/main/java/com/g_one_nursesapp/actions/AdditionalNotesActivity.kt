package com.g_one_nursesapp.actions

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.g_one_nursesapp.ChatFieldActivity
import com.g_one_nursesapp.R
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.viewmodels.AdditionalNoteViewModel
import kotlinx.android.synthetic.main.activity_additional_notes.*
import java.util.*

class AdditionalNotesActivity : AppCompatActivity() {

    private lateinit var result: String
    private lateinit var additionalNoteViewModel: AdditionalNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional_notes)

        additionalNoteViewModel = ViewModelProvider(this).get(AdditionalNoteViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Catatan Tambahan"

        val buttonSubmit = findViewById<TextView>(R.id.button_submite_additional)
        buttonSubmit.setOnClickListener {
            val addInput = inputAdditional.text.toString().trim()
            when {
                addInput.isEmpty() -> {
                    Toast.makeText(this, "Catatan belum dimasukan", Toast.LENGTH_LONG).show()
                }

                else -> {
                    val message = MessageEntity(
                        id = UUID.randomUUID().toString(),
                        message = "Catatan Tambahan",
                        result = addInput,
                    )
                    additionalNoteViewModel.insertOneMessage(message)

                    val intent = Intent(this, ChatFieldActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }


}