package com.hugg.main.fcm
import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
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
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hugg.main.MainActivity
import com.hugg.feature.R
import okhttp3.internal.notify

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

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("InvalidWakeLockTag")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == STOP_ALARM) {
            stopAlarm()
            stopSelf()
            return START_NOT_STICKY
        }

        if(checkPermission()) return START_NOT_STICKY

        val title = intent?.getStringExtra(FcmNotification.TITLE) ?: ""
        val body = intent?.getStringExtra(FcmNotification.BODY) ?: ""
        val navigation = intent?.getStringExtra(FcmNotification.NAVIGATION) ?: ""

        createNotificationChannel()

        val stopIntent = Intent(this, AlarmService::class.java).apply { action = STOP_ALARM }
        val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val mainPendingIntent = getPendingIntent(navigation)

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_app_logo_alarm)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(mainPendingIntent)
            .setDeleteIntent(stopPendingIntent)
            .build()

        notificationManager.notify(1, notification)

        Handler(Looper.getMainLooper()).postDelayed({
            startAlarm()
        }, 1000)

        return START_NOT_STICKY
    }


    private fun startAlarm(){
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if(checkPermission()) return

        val pattern = LongArray(REPEAT_TIME * 2) { i ->
            if (i % 2 == 0) DELAY_TIME else 500L
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createWaveform(pattern, -1) // 반복 없음
            vibrator?.vibrate(effect)
        } else {
            vibrator?.vibrate(pattern, -1)
        }

        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, alarmUri)
        ringtone?.play()
    }

    private fun checkPermission() : Boolean{
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
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

    private fun getPendingIntent(navigation: String): PendingIntent {
        val intent = if (isAppInForeground()) {
            Intent(applicationContext, MainActivity::class.java).apply {
                putExtra(STOP_ALARM, true)
                putExtra(PendingExtraValue.KEY, navigation)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
        } else {
            Intent(applicationContext, MainActivity::class.java).apply {
                putExtra(STOP_ALARM, true)
                putExtra(PendingExtraValue.KEY, navigation)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
        }

        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    private fun isAppInForeground(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses ?: return false

        for (processInfo in runningAppProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (processInfo.processName == packageName) {
                    return true
                }
            }
        }
        return false
    }
}