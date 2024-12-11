package com.hugg.domain.model.response.notification

data class NotificationHistoryResponseVo(
    val itemList : List<NotificationHistoryItemResponseVo> = emptyList(),
    val currentPage : Int = -1,
    val totalPage : Int = -1
)
