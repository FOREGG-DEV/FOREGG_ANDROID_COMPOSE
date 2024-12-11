package com.hugg.domain.model.response.notification

import com.hugg.domain.model.enums.NotificationType

data class NotificationHistoryItemResponseVo(
    val id : Long = -1,
    val targetKey : String = "",
    val notificationType : NotificationType,
    val sender : String = "",
    val elapsedTime : String = "",
)