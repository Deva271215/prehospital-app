package com.g_one_nursesapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.g_one_nursesapp.databinding.ActivityMainBinding
import com.g_one_nursesapp.entity.MessageEntity
import com.g_one_nursesapp.preference.UserPreference
import com.g_one_nursesapp.viewmodels.MainViewModel
import com.google.android.gms.tasks.Tasks
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var photoFile: File
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var mainViewModel: MainViewModel
    private lateinit var preference: UserPreference
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        preference = UserPreference(applicationContext)
        preference.setIsHospitalSelected(false)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.deleteMessages()

        setBottomSheetStart()

        binding.toolbar.setNavigationOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.mulaiButton.setOnClickListener { bottomSheetDialog.show() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.icon_history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomSheetStart(){
        bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetFragmentTheme)

        val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
            R.layout.fragment_open_cam,
            findViewById<LinearLayout>(R.id.openCamFragment)
        )

        bottomSheetView.findViewById<View>(R.id.button_openCam).setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)

            val fileProvider = FileProvider.getUriForFile(this, "com.g_one_nursesapp.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (takePictureIntent.resolveActivity(this.packageManager) != null){
                startActivityForResult(takePictureIntent, REQUEST_CODE)
                bottomSheetDialog.dismiss()
            } else {
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
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            // Upload photo to Firebase Storage
            val storageRef = FirebaseStorage.getInstance().reference
            val file = Uri.fromFile(photoFile.absoluteFile)
            val uploadRef = storageRef.child("uploads/${photoFile.name}")
            Thread {
                val response = Tasks.await(uploadRef.putFile(file))
                // Create message
                val uri = Tasks.await(response.storage.downloadUrl)
                val messageId = UUID.randomUUID().toString()
                val message = MessageEntity(
                        id = messageId,
                        message = "Posisi awal",
                        attachments = uri.toString()
                )
                mainViewModel.insertOneMessage(message)
            }.start()

            val intent = Intent(this, ChatFieldActivity::class.java)
            startActivity(intent)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        private const val FILE_NAME = "upload_"
        private const val REQUEST_CODE = 42
    }

}