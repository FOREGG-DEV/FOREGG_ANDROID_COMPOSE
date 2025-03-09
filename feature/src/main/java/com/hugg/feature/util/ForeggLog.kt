package com.hugg.feature.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log

object ForeggLog {

    fun D(msg : String) {
        Log.d("FOREGG LOG", msg)
    }

    fun D(log : String, msg : String) {
        Log.d(log, msg)
    }
}

fun openBatteryOptimizationSettings(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val packageName = context.packageName
        val pm = context.getSystemService(Context.POWER_SERVICE) as android.os.PowerManager

        if (pm.isIgnoringBatteryOptimizations(packageName)) {
            ForeggLog.D("이미 배터리 최적화 예외 설정됨")
            return
        }

        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
            data = Uri.parse("package:$packageName")
        }
        try {
            context.startActivity(intent)
            ForeggLog.D("배터리 최적화 예외 요청 화면 열기 성공")
        } catch (e: Exception) {
            ForeggLog.D("배터리 최적화 예외 요청 실패: ${e.message}")
        }
    }
}