package com.g_one_nursesapp

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.g_one_nursesapp.adapters.ChatFieldAdapter
import com.g_one_nursesapp.entity.AttachmentEntity
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.preference.UserPreference
import com.g_one_nursesapp.viewmodels.ChatFieldViewModel
import com.g_one_nursesapp.viewmodels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_chat_field.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var photoFile: File
    private lateinit var bottomSheetDialog: BottomSheetDialog

    private lateinit var mainViewModel: MainViewModel
    private lateinit var preference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        preference = UserPreference(applicationContext)
        preference.setSelectedHospital(false)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.deleteMessages()

        toolbar.setNavigationOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val btnStart = findViewById<View>(R.id.mulai_button)
        btnStart.setOnClickListener{
            bottomSheetDialog.show()
        }

        setBottomSheetStart()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.icon_history -> {
                val btnHistory = findViewById<View>(R.id.icon_history)
                btnHistory.setOnClickListener {
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun setBottomSheetStart(){
        bottomSheetDialog = BottomSheetDialog(
            this, R.style.BottomSheetFragmentTheme
        )

        val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
            R.layout.fragment_open_cam,
            findViewById<LinearLayout>(R.id.openCamFragment)
        )

        bottomSheetView.findViewById<View>(R.id.button_openCam).setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(Companion.FILE_NAME)

            val fileProvider = FileProvider.getUriForFile(this, "com.g_one_nursesapp.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if(takePictureIntent.resolveActivity(this.packageManager) != null){
                startActivityForResult(takePictureIntent, Companion.REQUEST_CODE)
                bottomSheetDialog.dismiss()
            }else{
                Toast.makeText(this, "Can't Open Camera", Toast.LENGTH_SHORT).show()
            }
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.dismiss()
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            // Create message
            val messageId = UUID.randomUUID().toString()
            val message = MessageEntity(
                id = messageId,
                message = "Posisi awal",
                result = null,
                condition = null,
                response = null,
                action = null,
                time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")).toString(),
            )
            mainViewModel.insertOneMessage(message)

            // Create attachment
            val attachment = AttachmentEntity(
                id = UUID.randomUUID().toString(),
                source = photoFile.absolutePath,
                messageId = messageId
            )
            mainViewModel.insertOneAttachment(attachment)

            val intent = Intent(this, ChatFieldActivity::class.java)
            startActivity(intent)
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val FILE_NAME = "upload_"
        private const val REQUEST_CODE = 42
    }

}