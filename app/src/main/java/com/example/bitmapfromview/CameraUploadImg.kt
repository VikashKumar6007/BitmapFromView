package com.example.bitmapfromview

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class CameraUploadImg : AppCompatActivity() {

    lateinit var imgView :ImageView
    lateinit var btnChange:Button
    lateinit var imageUrl :Uri
    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()){
       imgView.setImageURI(null)
        imgView.setImageURI(imageUrl)
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_camera_upload_img)
        imgView = findViewById(R.id.imageView)
        btnChange = findViewById(R.id.btnUplImg)

        imageUrl = createImageUri()!!
        btnChange.setOnClickListener {
            contract.launch(imageUrl)
        }

    }

    private fun createImageUri(): Uri? {
        val image = File(applicationContext.filesDir,"camera_photo.png")
        return FileProvider.getUriForFile(applicationContext,
            "com.example.bitmapfromview.fileProvider",
            image
        )
    }
}