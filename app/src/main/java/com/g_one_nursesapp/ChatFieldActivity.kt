package com.g_one_nursesapp

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.g_one_nursesapp.adapters.ChatFieldAdapter
import com.g_one_nursesapp.api.response.ChatResponse
import com.g_one_nursesapp.api.response.HospitalsResponse
import com.g_one_nursesapp.api.socket.InitChatPayload
import com.g_one_nursesapp.api.socket.SendBulkMessagesPayload
import com.g_one_nursesapp.entity.AttachmentEntity
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.preference.UserPreference
import com.g_one_nursesapp.utility.SocketIOInstance
import com.g_one_nursesapp.viewmodels.ChatFieldViewModel
import com.github.nkzawa.socketio.client.Ack
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat_field.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.schedule

class ChatFieldActivity : AppCompatActivity() {
    companion object {
        const val IS_HOSPITAL_SELECTED = "IS_HOSPITAL_SELECTED"
        const val SELECTED_HOSPITAL = "SELECTED_HOSPITAL"
    }

    private lateinit var chatFieldViewModel: ChatFieldViewModel
    private lateinit var chatFieldAdapter: ChatFieldAdapter
    private lateinit var preference: UserPreference
    private lateinit var createdChat: ChatResponse

    private var gson = Gson()
    private var messageResults = ArrayList<SendBulkMessagesPayload>()

    private val socketIOInstance = SocketIOInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_field)

        setSupportActionBar(toolbar)

        preference = UserPreference(applicationContext)

        chatFieldAdapter = ChatFieldAdapter()
        rvChatField.layoutManager = LinearLayoutManager(applicationContext)
        rvChatField.adapter = chatFieldAdapter

        chatFieldViewModel = ViewModelProvider(this).get(ChatFieldViewModel::class.java)

        // Get all messages
        // Send all local messages to remote
        chatFieldViewModel.fetchMessages.observe(this@ChatFieldActivity, {
            messageResults = ArrayList<SendBulkMessagesPayload>()
            for (item in it) {
                val message = SendBulkMessagesPayload(
                        message = item.message.message,
                        creationTime = item.message.creationTime!!,
                        result = item.message.result,
                        response = item.message.response,
                        condition = item.message.condition,
                        action = item.message.action,
                )
                messageResults.add(message)
            }
        })

        // Get data from intent
        val isHospitalSelected = intent.getBooleanExtra(IS_HOSPITAL_SELECTED, false)
        val selectedHospital = gson.fromJson(intent.getStringExtra(SELECTED_HOSPITAL), HospitalsResponse::class.java)
        if (isHospitalSelected) {
            // Set selected hospital to shared preferences
            preference.setIsHospitalSelected(isHospitalSelected)
            preference.setSelectedHospital(selectedHospital)

            // Connect to socket
            socketIOInstance.connectToSocketServer()
            socketIOInstance.getSocket()?.connect()

            // Emit init_chat event to server via socket
            val initChatPayload = InitChatPayload(preference.getLoginData().user!!, selectedHospital)
            socketIOInstance.getSocket()?.emit("init_chat", gson.toJson(initChatPayload), object: Ack {
                override fun call(vararg args: Any?) {
                    // Set currently active chat to shared preferences
                    val newChat = args[0]
                    val json = gson.fromJson("""$newChat""", ChatResponse::class.java)
                    preference.setActiveChat(json)
                    createdChat = json

                    // Append chat in messageResults variable
                    val tempResult = ArrayList<SendBulkMessagesPayload>()
                    for (m in messageResults) {
                        m.chat = json
                        tempResult.add(m)
                    }
                    socketIOInstance.getSocket()?.emit("send_bulk_messages", gson.toJson(tempResult))
                }
            })
        }

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

    override fun onDestroy() {
        super.onDestroy()
        socketIOInstance.getSocket()?.disconnect()
    }
}