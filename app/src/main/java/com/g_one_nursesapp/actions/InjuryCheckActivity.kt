package com.g_one_nursesapp.actions

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.g_one_nursesapp.R
import kotlinx.android.synthetic.main.activity_conscious_check.*
import kotlinx.android.synthetic.main.activity_injury_check.*
import kotlinx.android.synthetic.main.activity_tensi_check.*
import kotlinx.android.synthetic.main.fragment_check_conscious.*

class InjuryCheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_injury_check)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Periksa Cidera"

        val spInjury1 = listOf("Masukan cidera", "Tidak ada respon mata (1)", "Bereaksi saat rangsang nyeri (2)", "Beraksi saat diperintah buka mata (3)", "Mata terbuka normal tanpa rangsangan dan perintah (4)")
        val adapterInjury1 = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spInjury1)
        inputInjury1.adapter = adapterInjury1
        inputInjury1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                Toast.makeText(
                        this@InjuryCheckActivity,
                        "You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        val spInjury2 = listOf("Masukan cidera lainnya", "Tidak ada respon suara (1)", "Bereaksi saat rangsang nyeri (2)", "Beraksi saat diperintah buka mata (3)", "Mata terbuka normal tanpa rangsangan dan perintah (4)")
        val adapterInjury2 = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spInjury2)
        inputInjury2.adapter = adapterInjury2
        inputInjury2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                Toast.makeText(
                        this@InjuryCheckActivity,
                        "You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val spListInjury3 = listOf("Masukan cidera lainnya", "Tidak ada respon gerakan (1)", "Bereaksi saat rangsang nyeri (2)", "Beraksi saat diperintah buka mata (3)", "Mata terbuka normal tanpa rangsangan dan perintah (4)")
        val adapterInjury3 = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spListInjury3)
        inputInjury3.adapter = adapterInjury3
        inputInjury3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                Toast.makeText(
                        this@InjuryCheckActivity,
                        "You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val spListInjury4 = listOf("Masukan cidera lainnya", "Tidak ada respon gerakan (1)", "Bereaksi saat rangsang nyeri (2)", "Beraksi saat diperintah buka mata (3)", "Mata terbuka normal tanpa rangsangan dan perintah (4)")
        val adapterInjury4 = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spListInjury4)
        inputInjury4.adapter = adapterInjury4
        inputInjury4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                Toast.makeText(
                        this@InjuryCheckActivity,
                        "You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val spListInjury5 = listOf("Masukan cidera lainnya", "Tidak ada respon gerakan (1)", "Bereaksi saat rangsang nyeri (2)", "Beraksi saat diperintah buka mata (3)", "Mata terbuka normal tanpa rangsangan dan perintah (4)")
        val adapterInjury5 = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spListInjury5)
        inputInjury5.adapter = adapterInjury5
        inputInjury5.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                Toast.makeText(
                        this@InjuryCheckActivity,
                        "You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val spListInjury6 = listOf("Masukan cidera lainnya", "Tidak ada respon gerakan (1)", "Bereaksi saat rangsang nyeri (2)", "Beraksi saat diperintah buka mata (3)", "Mata terbuka normal tanpa rangsangan dan perintah (4)")
        val adapterInjury6 = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spListInjury6)
        inputInjury6.adapter = adapterInjury6
        inputInjury6.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                Toast.makeText(
                        this@InjuryCheckActivity,
                        "You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val spListInjury7 = listOf("Masukan cidera lainnya", "Tidak ada respon gerakan (1)", "Bereaksi saat rangsang nyeri (2)", "Beraksi saat diperintah buka mata (3)", "Mata terbuka normal tanpa rangsangan dan perintah (4)")
        val adapterInjury7 = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spListInjury7)
        inputInjury7.adapter = adapterInjury7
        inputInjury7.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                Toast.makeText(
                        this@InjuryCheckActivity,
                        "You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

//        setButtonSubmit()
    }

//    private fun setButtonSubmit(){
//        button_submite.setOnClickListener {
//            Toast.makeText(this@InjuryCheckActivity, "Value Submited", Toast.LENGTH_LONG).show()
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}