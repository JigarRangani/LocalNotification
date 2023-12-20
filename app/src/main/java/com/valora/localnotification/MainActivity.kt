package com.valora.localnotification

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                setDailyNotification()
            } else {
                showPermissionSnackbar()
            }
        }

        if (shouldRequestNotificationPermission()) {
            requestNotificationPermission()
        } else {
            setDailyNotification()
        }
    }

    private fun shouldRequestNotificationPermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun showPermissionSnackbar() {
        Snackbar.make(
            findViewById<View>(android.R.id.content).rootView,
            "Please grant Notification permission from App Settings",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun setDailyNotification() {
        val notificationScheduler = NotificationScheduler(this)
        notificationScheduler.scheduleDailyNotification()
        Toast.makeText(this, "Daily notification set for 8 AM", Toast.LENGTH_SHORT).show()
    }
}