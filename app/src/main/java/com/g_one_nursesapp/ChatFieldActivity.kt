package com.g_one_nursesapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.g_one_nursesapp.adapters.ChatFieldAdapter
import com.g_one_nursesapp.databinding.ActivityChatFieldBinding
import com.g_one_nursesapp.preference.UserPreference
import com.g_one_nursesapp.utility.SocketIOInstance
import com.g_one_nursesapp.viewmodels.ChatFieldViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_chat_field.*
import kotlinx.android.synthetic.main.activity_main.toolbar

class ChatFieldActivity : AppCompatActivity() {
    companion object {
        const val IS_HOSPITAL_SELECTED = "IS_HOSPITAL_SELECTED"
        const val SELECTED_HOSPITAL = "SELECTED_HOSPITAL"
    }

    private lateinit var chatFieldViewModel: ChatFieldViewModel
    private lateinit var chatFieldAdapter: ChatFieldAdapter
    private lateinit var preference: UserPreference
    private lateinit var binding: ActivityChatFieldBinding
    private val socketIOInstance = SocketIOInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = UserPreference(applicationContext)
        binding = ActivityChatFieldBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbar)

        // Adapter
        chatFieldAdapter = ChatFieldAdapter()
        rvChatField.layoutManager = LinearLayoutManager(applicationContext)
        rvChatField.adapter = chatFieldAdapter

        // View model
        chatFieldViewModel = ViewModelProvider(this).get(ChatFieldViewModel::class.java)

        onBackButtonHandler()
        onActionButtonClicked()
        displayMessagesFromRoom()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.done_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        socketIOInstance.getSocket()?.disconnect()
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
}