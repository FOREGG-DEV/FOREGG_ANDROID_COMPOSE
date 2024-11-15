package com.hugg.data.dto.dailyHugg

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.enums.DailyHuggReplyType

data class DailyHuggResponse(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("date")
    val date: String = "",
    @SerializedName("day")
    val day: String = "",
    @SerializedName("dailyConditionType")
    val dailyConditionType: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("replyContent")
    val replyContent: String? = null,
    @SerializedName("replyEmojiType")
    val replyEmojiType: DailyHuggReplyType? = DailyHuggReplyType.NOTHING,
    @SerializedName("specialQuestion")
    val specialQuestion: String? = null,
)
