package com.hugg.data.dto.notification

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.NotificationType

data class NotificationHistoryItemResponse(
    @SerializedName("id")
    val id : Long = -1,
    @SerializedName("targetKey")
    val targetKey : String = "",
    @SerializedName("notificationType")
    val notificationType : NotificationType = NotificationType.SUPPORT,
    @SerializedName("sender")
    val sender : String = "",
    @SerializedName("createdAt")
    val createdAt : String = "",
    @SerializedName("elapsedTime")
    val elapsedTime : String = "",
)
