package com.hugg.feature.util

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.hugg.feature.databinding.CommonToastBinding

object HuggToast {

    private const val MARGIN_BOTTOM = 59

    fun createToast(context: Context, message: String, length : Int = Toast.LENGTH_SHORT): Toast {
        val inflater = LayoutInflater.from(context)
        val binding = CommonToastBinding.inflate(inflater, null, false)
        binding.textMessage.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, MARGIN_BOTTOM.px)
            duration = length
            view = binding.root
        }
    }
}

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()