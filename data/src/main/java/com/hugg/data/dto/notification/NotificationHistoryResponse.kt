package com.hugg.data.dto.notification

import com.google.gson.annotations.SerializedName

data class NotificationHistoryResponse(
    @SerializedName("dto")
    val dto : List<NotificationHistoryItemResponse> = emptyList(),
    @SerializedName("currentPage")
    val currentPage : Int = 0,
    @SerializedName("totalPage")
    val totalPage : Int = 0,
    @SerializedName("totalElements")
    val totalElements : Int = 0,
)
