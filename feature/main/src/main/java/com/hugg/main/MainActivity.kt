package com.hugg.main

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hugg.feature.util.ForeggLog
import com.hugg.main.fcm.AlarmService
import com.hugg.main.fcm.PendingExtraValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navigation = intent.getStringExtra(PendingExtraValue.KEY) ?: ""
        if(intent.getBooleanExtra(AlarmService.STOP_ALARM, false)) {
            val stopIntent = Intent(this, AlarmService::class.java).apply {
                action = AlarmService.STOP_ALARM
            }
            startService(stopIntent)
        }

        setContent {
            MainScreen(
                initialNavigation = navigation
            )
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            it.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}