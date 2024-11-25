package com.hugg.home.notification

import com.hugg.domain.model.response.notification.NotificationHistoryItemResponseVo
import com.hugg.feature.base.PageState

data class NotificationPageState(
    val itemList : List<NotificationHistoryItemResponseVo> = emptyList(),
    val currentPage : Int = -1,
    val totalPage : Int = -1
) : PageState