package com.g_one_nursesapp.actions

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.g_one_nursesapp.databinding.ActivityInjuryCheckBinding
import com.g_one_nursesapp.faskes.RecomendFaskesActivity
import kotlinx.android.synthetic.main.activity_injury_check.*
import kotlin.collections.ArrayList

class InjuryCheckActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInjuryCheckBinding

    private val injuriesList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInjuryCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                selectInjuriesInput.setText(items.joinToString { "$it" })
            }
            builder.show()
        }
    }

    private fun onSubmitButtonClicked() {
        button_submite.setOnClickListener {
            val intent = Intent(this, RecomendFaskesActivity::class.java)
            startActivity(intent)
        }
    }
}
