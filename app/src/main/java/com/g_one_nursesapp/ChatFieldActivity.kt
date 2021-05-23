package com.g_one_nursesapp

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.g_one_nursesapp.adapters.ChatFieldAdapter
import com.g_one_nursesapp.entity.AttachmentEntity
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.viewmodels.ChatFieldViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_chat_field.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import java.io.File
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class ChatFieldActivity : AppCompatActivity() {
    private lateinit var chatFieldViewModel: ChatFieldViewModel
    private lateinit var chatFieldAdapter: ChatFieldAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_field)

        setSupportActionBar(toolbar)

        chatFieldAdapter = ChatFieldAdapter()
        rvChatField.layoutManager = LinearLayoutManager(applicationContext)
        rvChatField.adapter = chatFieldAdapter

        chatFieldViewModel = ViewModelProvider(this).get(ChatFieldViewModel::class.java)

        toolbar.setNavigationOnClickListener{

            val bottomSheetDialogAlertBack = BottomSheetDialog(
                this@ChatFieldActivity, R.style.BottomSheetFragmentTheme
            )

            val bottomSheetViewAlertBack = LayoutInflater.from(applicationContext).inflate(
                R.layout.fragment_back_alert,
                findViewById<LinearLayout>(R.id.backAlertFragment)
            )

            bottomSheetViewAlertBack.findViewById<View>(R.id.button_yakin).setOnClickListener{
                onBackPressed()
                chatFieldViewModel.deleteMessages()
                bottomSheetDialogAlertBack.dismiss()
            }

            bottomSheetDialogAlertBack.setContentView(bottomSheetViewAlertBack)
            bottomSheetDialogAlertBack.show()
        }

        chatFieldViewModel.fetchMessages.observe(this, {
            chatFieldAdapter.setMessage(it)
        })

        setButtonTindakan()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.done_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.icon_check -> {

                val bottomSheetDone = BottomSheetDialog(
                    this@ChatFieldActivity, R.style.BottomSheetFragmentTheme
                )

                val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                    R.layout.fragment_done,
                    findViewById<LinearLayout>(R.id.fragmentDone)
                )

                bottomSheetView.findViewById<View>(R.id.button_done).setOnClickListener {
                    val intent = Intent(this, PredictionsActivity::class.java)
                    startActivity(intent)
                }

                bottomSheetDone.setContentView(bottomSheetView)
                bottomSheetDone.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setButtonTindakan(){
        btnTindakan.setOnClickListener{
            val intent = Intent(this, ListActionsActivity::class.java)
            startActivity(intent)
        }
    }
}