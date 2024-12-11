package com.hugg.main.fcm

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.os.Vibrator
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hugg.main.MainActivity
import com.hugg.feature.R

class AlarmService : Service() {

    companion object{
        const val STOP_ALARM = "STOP_ALARM"
        const val CHANNEL_ID = "alarm_service_channel"
        const val REPEAT_TIME = 10
        const val DELAY_TIME = 1000L //ms
        var ringtone: Ringtone? = null
        var vibrator: Vibrator? = null

        fun stopAlarm() {
            ringtone?.stop()
            vibrator?.cancel()
        }
    }

    @SuppressLint("InvalidWakeLockTag")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == STOP_ALARM) {
            stopAlarm()
            stopSelf()
            return START_NOT_STICKY
        }
        val title = intent?.getStringExtra(FcmNotification.TITLE) ?: ""
        val body = intent?.getStringExtra(FcmNotification.BODY) ?: ""
        val navigation = intent?.getStringExtra(FcmNotification.NAVIGATION) ?: ""

        createNotificationChannel()

        val mainIntent = Intent(applicationContext, MainActivity::class.java).apply {
            putExtra(STOP_ALARM, true)
            putExtra(PendingExtraValue.KEY, navigation)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val stopIntent = Intent(this, AlarmService::class.java).apply { action = STOP_ALARM }
        val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notificationManager = NotificationManagerCompat.from(applicationContext)

        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_app_logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setDeleteIntent(stopPendingIntent)
            .build()

        checkPermission()
        startForeground(1, notificationBuilder)

        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG")
        wakeLock.acquire(3000)
        wakeLock.release()
        notificationManager.notify(1, notificationBuilder)
        startAlarm()
        return START_STICKY
    }

    private fun startAlarm(){
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val singlePattern = longArrayOf(DELAY_TIME, DELAY_TIME)
        val pattern = longArrayOf(0) + singlePattern.repeat(REPEAT_TIME)
        vibrator?.vibrate(pattern, -1)

        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, alarmUri)
        ringtone?.play()
    }

    private fun checkPermission(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Alarm Service Channel", NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun LongArray.repeat(times: Int): LongArray {
        return LongArray(this.size * times) { this[it % this.size] }
    }
}