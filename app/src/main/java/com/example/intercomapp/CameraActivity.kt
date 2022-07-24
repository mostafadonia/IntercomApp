package com.example.intercomapp

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class CameraActivity : AppCompatActivity() {
    private lateinit var cameraBtn:Button
    private lateinit var myImage: ImageView
    private lateinit var pictureBack:Button
    private lateinit var pictureSave: Button
    private val cameraRequestId = 1222

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        cameraBtn = findViewById(R.id.picture_button)
        myImage = findViewById(R.id.myImage)
        pictureBack = findViewById(R.id.picture_back)
        pictureSave = findViewById(R.id.picture_save_button)
        //get Permission
        if(ContextCompat.checkSelfPermission(
                applicationContext,android.Manifest.permission.CAMERA
        )==PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.CAMERA),
                cameraRequestId
            )
        // set Camera Open
        cameraBtn.setOnClickListener {
            val cameraInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraInt,cameraRequestId)
        }

        //back button
        pictureBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == cameraRequestId){
            //save to Image in Layout
            val images:Bitmap = data?.extras?.get("data") as Bitmap
            myImage.setImageBitmap(images)
        }
    }
}