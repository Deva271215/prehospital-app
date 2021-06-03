package com.g_one_nursesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.g_one_nursesapp.databinding.ActivityImageViewBinding
import kotlinx.android.synthetic.main.activity_image_view.*

class ImageViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageViewBinding

    companion object {
        const val IMAGE_VIEW = "IMAGE_VIEW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageSrc = intent.getStringExtra(IMAGE_VIEW)
        Glide.with(this)
            .load(imageSrc)
            .into(binding.tivImage)

        closeFun()
    }

    private fun closeFun() {
        backButton.setOnClickListener{
            onBackPressed()
        }
    }
}