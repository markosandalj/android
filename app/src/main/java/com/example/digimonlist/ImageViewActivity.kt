package com.example.digimonlist

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ImageViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)

        val imageView: ImageView = findViewById(R.id.fullImageView)

        val digimonImage = intent.getIntExtra("digimon_image", 0)
        imageView.setImageResource(digimonImage)
    }
}
