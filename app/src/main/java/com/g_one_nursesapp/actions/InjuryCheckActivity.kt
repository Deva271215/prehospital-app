package com.g_one_nursesapp.actions

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.g_one_nursesapp.ChatFieldActivity
import com.g_one_nursesapp.databinding.ActivityInjuryCheckBinding
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.faskes.RecomendFaskesActivity
import com.g_one_nursesapp.viewmodels.InjuryCheckViewModel
import kotlinx.android.synthetic.main.activity_injury_check.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class InjuryCheckActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInjuryCheckBinding
    private lateinit var injuryCheckViewModel: InjuryCheckViewModel

    private val injuriesList = ArrayList<Int>()
    private lateinit var injuriesString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInjuryCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val injuries = arrayOf(
                "Patah tulang",
                "Luka lecet",
                "Pendarahan di kepala",
                "Lengan kanan berdarah",
                "Lengan kiri berdarah",
                "Kaki kanan berdarah",
                "Kaki kiri berdarah",
                "Punggung terluka"
        )
        val selectedInjuries = BooleanArray(injuries.size)

        // Initialize dialog
        selectInjuriesInput.isFocusable = false
        selectInjuriesInput.isClickable = true
        selectInjuriesInput.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pilih cidera")
            builder.setCancelable(false)
            builder.setMultiChoiceItems(injuries, selectedInjuries) { _, which, isChecked ->
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
                    items.add(injuries[injury])
                }
                injuriesString = items.joinToString { "$it" }
                selectInjuriesInput.setText(items.joinToString { "$it" })
            }
            builder.show()
        }
    }

    private fun onSubmitButtonClicked() {
        button_submite.setOnClickListener {
            val message = MessageEntity(
                    id = UUID.randomUUID().toString(),
                    message = "Periksa Cidera",
                    result = injuriesString,
                    condition = null,
                    response = null,
                    action = null,
                    time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).toString()
            )
            injuryCheckViewModel.insertOneMessage(message)

            val intent = Intent(this, RecomendFaskesActivity::class.java)
            startActivity(intent)
        }
    }
}
