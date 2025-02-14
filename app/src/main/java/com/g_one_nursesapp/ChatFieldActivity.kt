package com.g_one_nursesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.g_one_nursesapp.adapters.ChatFieldAdapter
import com.g_one_nursesapp.api.FirebaseRESTClient
import com.g_one_nursesapp.api.MLAPIClient
import com.g_one_nursesapp.api.RetrofitClient
import com.g_one_nursesapp.api.requests.DataSchema
import com.g_one_nursesapp.api.requests.NotificationRequest
import com.g_one_nursesapp.api.response.ChatResponse
import com.g_one_nursesapp.api.response.HospitalsResponse
import com.g_one_nursesapp.api.response.NotificationResponse
import com.g_one_nursesapp.api.response.PredictionResponse
import com.g_one_nursesapp.api.socket.InitChatPayload
import com.g_one_nursesapp.api.socket.SendBulkMessagesPayload
import com.g_one_nursesapp.databinding.ActivityChatFieldBinding
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.preference.UserPreference
import com.g_one_nursesapp.utility.Global
import com.g_one_nursesapp.utility.SocketIOInstance
import com.g_one_nursesapp.viewmodels.ChatFieldViewModel
import com.github.nkzawa.socketio.client.Ack
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatFieldActivity : AppCompatActivity() {
    companion object {
        const val IS_HOSPITAL_SELECTED = "IS_HOSPITAL_SELECTED"
        const val IMAGE_VIEW = "IMAGE_VIEW"
        const val SELECTED_HOSPITAL = "SELECTED_HOSPITAL"
        const val CHAT_ROOM_ID = "CHAT_ROOM_ID"
        const val IS_FROM_HISTORY = "IS_FROM_HISTORY"
    }

    private lateinit var chatFieldViewModel: ChatFieldViewModel
    private lateinit var chatFieldAdapter: ChatFieldAdapter
    private lateinit var preference: UserPreference
    private lateinit var binding: ActivityChatFieldBinding
    private val socket = SocketIOInstance()
    private var messages = ArrayList<SendBulkMessagesPayload>()
    private var predictions = ArrayList<PredictionResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = UserPreference(applicationContext)
        binding = ActivityChatFieldBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbar)

        // Adapter
        val layoutManager = LinearLayoutManager(applicationContext)
        chatFieldAdapter = ChatFieldAdapter()
        binding.rvChatField.layoutManager = layoutManager
        layoutManager.stackFromEnd = true
        binding.rvChatField.adapter = chatFieldAdapter

        // View model
        chatFieldViewModel = ViewModelProvider(this).get(ChatFieldViewModel::class.java)

        onBackButtonHandler()
        onActionButtonClicked()

        val isFromHistory = intent.getBooleanExtra(IS_FROM_HISTORY, false)
        if (isFromHistory) {
            binding.btnTindakan.visibility = View.GONE
            loadMessagesBasedOnChatId()
        } else {
            displayMessagesFromRoom()
        }

        val isHospitalSelected = intent.getBooleanExtra(IS_HOSPITAL_SELECTED, false)
        val selectedHospital = intent.getStringExtra(SELECTED_HOSPITAL)
        if (isHospitalSelected) {
            // Save selected hospital data to preferences
            preference.setIsHospitalSelected(isHospitalSelected)
            preference.setSelectedHospital(selectedHospital!!)

            getAllMessagesFromLocalDatabase(selectedHospital)

            val hospitalId = Gson().fromJson(selectedHospital, HospitalsResponse::class.java).id
            sendNotification(hospitalId)
        }

        Log.i("symtomps", Global.symtomps.toString())
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!intent.getBooleanExtra(IS_FROM_HISTORY, false)) {
            menuInflater.inflate(R.menu.done_menu, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.getSocket()?.disconnect()
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
                    makePrediction()
                }
                bottomSheetDone.setContentView(bottomSheetView)
                bottomSheetDone.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onBackButtonHandler() {
        binding.toolbar.setNavigationOnClickListener{
            val bottomSheetDialogAlertBack = BottomSheetDialog(
                    this@ChatFieldActivity,
                    R.style.BottomSheetFragmentTheme
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
    }

    private fun onActionButtonClicked(){
        binding.btnTindakan.setOnClickListener{
            val intent = Intent(this, ListActionsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayMessagesFromRoom() {
        chatFieldViewModel.fetchMessages.observe(this, {
            chatFieldAdapter.setMessages(it)
        })
    }

    private fun initChatAndMessagesToServer(selectedHospital: String) {
        socket.initSocket()
        socket.connectToSocket()

        val user = preference.getLoginData().user
        val hospital = Gson().fromJson("""$selectedHospital""", HospitalsResponse::class.java)
        val initChatPayload = InitChatPayload(user!!, hospital)
        socket.getSocket()?.emit("init_chat", Gson().toJson(initChatPayload), object: Ack {
            override fun call(vararg args: Any?) {
                // Save currently active chat to SharedPreferences
                val res = args[0]
                val chatInJson = Gson().fromJson("""$res""", ChatResponse::class.java)
                preference.setActiveChat(chatInJson)

                for (message in messages) {
                    message.chat = chatInJson
                }

                socket.getSocket()?.emit("join_chat")
                socket.getSocket()?.emit("send_bulk_messages", Gson().toJson(messages))
            }
        })
    }

    private fun getAllMessagesFromLocalDatabase(selectedHospital: String) {
        chatFieldViewModel.fetchMessages.observe(this@ChatFieldActivity, {
            messages = ArrayList<SendBulkMessagesPayload>()
            for (item in it) {
                val message = SendBulkMessagesPayload(
                        message = item.message,
                        creationTime = item.creationTime!!,
                        result = item.result,
                        response = item.response,
                        condition = item.condition,
                        action = item.action,
                        attachments = item.attachments
                )
                messages.add(message)
            }

            initChatAndMessagesToServer(selectedHospital)
        })
    }

    private fun loadMessagesBasedOnChatId() {
        val chatRoomId = intent.getStringExtra(CHAT_ROOM_ID)
        val token = preference.getLoginData()?.access_token
        RetrofitClient.instance.getMessages(chatRoomId!!, "Bearer $token").enqueue(object: Callback<ArrayList<MessageEntity>> {
            override fun onResponse(call: Call<ArrayList<MessageEntity>>, response: Response<ArrayList<MessageEntity>>) {
                if (response.isSuccessful) {
                    chatFieldAdapter.setMessages(response.body()!!)
                }
            }

            override fun onFailure(call: Call<ArrayList<MessageEntity>>, t: Throwable) {
                Toast.makeText(this@ChatFieldActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun makePrediction() {
        MLAPIClient.instance.predictSymtomps(Global.symtomps).enqueue(object: Callback<ArrayList<PredictionResponse>> {
            override fun onResponse(
                call: Call<ArrayList<PredictionResponse>>,
                response: Response<ArrayList<PredictionResponse>>,
            ) {
                if (response.isSuccessful) {
                    predictions = response.body()!!

                    val intent = Intent(this@ChatFieldActivity, PredictionsActivity::class.java)
                    intent.putExtra(PredictionsActivity.PREDICTION, Gson().toJson(predictions))
                    startActivity(intent)
                } else {
                    Toast.makeText(this@ChatFieldActivity, "Error occurred", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<PredictionResponse>>, t: Throwable) {
                Toast.makeText(this@ChatFieldActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun sendNotification(hospitalId: String) {
        val chatId = Gson().fromJson(preference.getActiveChat(), ChatResponse::class.java).id
        val dataSchema = DataSchema(
            title = "EMERGENCY",
            body = "Ketuk untuk melihat rekam medis pasien",
            clickAction = "MAINACTIVITY",
            chatId = chatId
        )
        val notificationRequest = NotificationRequest(topic = hospitalId, data = dataSchema)
        FirebaseRESTClient.instance.sendMotification(notificationRequest).enqueue(object: Callback<NotificationResponse> {
            override fun onResponse(
                call: Call<NotificationResponse>,
                response: Response<NotificationResponse>,
            ) {
                Toast.makeText(this@ChatFieldActivity, response.body()?.messageId!!, Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                Toast.makeText(this@ChatFieldActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
