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
import com.g_one_nursesapp.entity.AttachmentEntity
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.viewmodels.ChatFieldViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_chat_field.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import java.io.File
import java.util.*

private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE = 42
private lateinit var photoFile: File

class ChatFieldActivity : AppCompatActivity() {
    private lateinit var chatFieldViewModel: ChatFieldViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_field)

        setSupportActionBar(toolbar)

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
                bottomSheetDialogAlertBack.dismiss()
            }

            bottomSheetDialogAlertBack.setContentView(bottomSheetViewAlertBack)
            bottomSheetDialogAlertBack.show()
        }

        setBottomSheetStart()
        setButtonTindakan()
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

    private fun setBottomSheetStart(){
        val bottomSheetDialog = BottomSheetDialog(
                this@ChatFieldActivity, R.style.BottomSheetFragmentTheme
        )

        val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.fragment_open_cam,
                findViewById<LinearLayout>(R.id.openCamFragment)
        )

        bottomSheetView.findViewById<View>(R.id.button_openCam).setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)

            val fileProvider = FileProvider.getUriForFile(this, "com.g_one_nursesapp.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if(takePictureIntent.resolveActivity(this.packageManager) != null){
                startActivityForResult(takePictureIntent, REQUEST_CODE)
                bottomSheetDialog.dismiss()
            }else{
                Toast.makeText(this@ChatFieldActivity, "Can't Open Camera", Toast.LENGTH_SHORT).show()
            }
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    private fun setButtonTindakan(){
        btnTindakan.setOnClickListener{
            val intent = Intent(this, ListActionsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getPhotoFile(fileName: String): File{
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)

            // Create message
            val messageId = UUID.randomUUID().toString()
            val message = MessageEntity(id = messageId, message = "Posisi awal")
            chatFieldViewModel.insertOneMessage(message)

            // Create attachment
            val attachment = AttachmentEntity(
                id = UUID.randomUUID().toString(),
                source = photoFile.absolutePath,
                messageId = messageId
            )
            chatFieldViewModel.insertOneAttachment(attachment)

            docImage.setImageBitmap(takenImage)
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}