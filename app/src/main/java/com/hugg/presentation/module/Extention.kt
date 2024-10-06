package com.hugg.presentation.module

import android.os.Build
import android.os.Bundle
import java.io.Serializable

fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")
fun String?.isJsonArray(): Boolean = this?.startsWith("[") == true && this.endsWith("]")

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

const val BASE_URL = "http://ec2-15-164-204-24.ap-northeast-2.compute.amazonaws.com:8080"
