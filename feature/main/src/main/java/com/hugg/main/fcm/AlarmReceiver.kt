package com.hugg.main.fcm

import android.content.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, AlarmService::class.java).apply {
            putExtra(FcmNotification.TITLE, intent.getStringExtra(FcmNotification.TITLE))
            putExtra(FcmNotification.BODY, intent.getStringExtra(FcmNotification.BODY))
            putExtra(FcmNotification.NAVIGATION, intent.getStringExtra(FcmNotification.NAVIGATION))
        }
        AlarmService.enqueueWork(context, serviceIntent)
    }
}
