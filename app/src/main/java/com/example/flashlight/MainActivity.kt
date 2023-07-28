package com.example.flashlight

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if the flashlight is available
        val isFlashLight = application.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
        println("Has Flashlight: $isFlashLight")
        var cameraStatus = false

        // Get the camera ID
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]

        // Get the flash light icon
        val flashLight: ImageView = findViewById(R.id.flashLight)

        // Listen for when the icon is clicked
        flashLight.setOnClickListener {
            cameraStatus = !cameraStatus

            // Change the icon to a on flash light
            if (cameraStatus) {
                flashLight.setImageResource(R.drawable.torch_on)
            } else {
                flashLight.setImageResource(R.drawable.torch_off)
            }

            // Turn on the flash light
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cameraManager.setTorchMode(cameraId, cameraStatus)
                }
            } catch (e: CameraAccessException) {
                println("Failed to turn on. You don't have access.")
            }

        }
    }
}