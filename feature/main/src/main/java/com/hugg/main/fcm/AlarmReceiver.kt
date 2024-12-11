package com.hugg.main.fcm

import android.content.*
import android.os.Build

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, AlarmService::class.java).apply {
            putExtra(FcmNotification.TITLE, intent.getStringExtra(FcmNotification.TITLE))
            putExtra(FcmNotification.BODY, intent.getStringExtra(FcmNotification.BODY))
            putExtra(FcmNotification.NAVIGATION, intent.getStringExtra(FcmNotification.NAVIGATION))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}
