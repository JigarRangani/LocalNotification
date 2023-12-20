package com.valora.localnotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Device has finished booting, re-schedule the daily notification
            val notificationScheduler = NotificationScheduler(context)
            notificationScheduler.scheduleDailyNotification()
        }
    }
}
