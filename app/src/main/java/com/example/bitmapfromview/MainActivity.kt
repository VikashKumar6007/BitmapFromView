package com.example.bitmapfromview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var imageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val container = findViewById<LinearLayout>(R.id.main)


        val btn = findViewById<Button>(R.id.btn_ScreenShot)
        imageUri = createImageUri()

        btn.setOnClickListener {
            it.isVisible =false
            val bitmap = getBitmapFromView(container)
            Toast.makeText(this@MainActivity,"ScreenShort Click Successfully",Toast.LENGTH_SHORT).show()
            storeBitmap(bitmap)

        }


    }

    private fun storeBitmap(bitmap: Bitmap) {
        val outputStream = applicationContext.contentResolver.openOutputStream(imageUri)
        if (outputStream != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        outputStream!!.close()

    }


    private fun createImageUri(): Uri {
        val image = File(applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile(
            applicationContext,
            "com.example.bitmapfromview.fileProvider",
            image
        )

    }

    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bg = view.background
        if (bg != null) {
            bg.draw(canvas)
        } else {
            canvas.drawColor(0xFFFFFFFF.toInt()) // Optional: Fill canvas with white color if no background
        }
        view.draw(canvas)
        return bitmap

    }


}